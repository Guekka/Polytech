//#include <LibLacuna.h>
#include <I2S.h>
#include <Wire.h>
#include <stm32l4_sai.h>
#include <stm32l4_gpio.h>
#include <stm32l4_wiring_private.h>

#include "ADC3101.h"
#include "full_model.h"

#define I2S_SAMPLE_RATE 16000  // [16000, 48000] supported by the microphone
#define I2S_BITS_PER_SAMPLE 16 // I2S wordlength is 16

static number_t inputs[MODEL_INPUT_CHANNELS][MODEL_INPUT_SAMPLES]; // 1-channel, 16000 samples for 16kHz over 1s
static volatile size_t sample_i = 0; // Index for inputs array samples dimension
static number_t outputs[MODEL_OUTPUT_SAMPLES];
static volatile boolean ready_for_inference = false; // Set to true when sample_i reaches the end of the inputs array

// Nucleo-L476RG I2C3 on A5/A4
extern const stm32l4_i2c_pins_t g_Wire1Pins = { GPIO_PIN_PC0_I2C3_SCL, GPIO_PIN_PC1_I2C3_SDA };
extern const unsigned int g_Wire1Instance = I2C_INSTANCE_I2C3;
extern const unsigned int g_Wire1Mode = I2C_MODE_RX_DMA;
static stm32l4_i2c_t _Wire1;

TwoWire Wire1(&_Wire1, g_Wire1Instance, &g_Wire1Pins, STM32L4_I2C_IRQ_PRIORITY, g_Wire1Mode);

// Nucleo-L476RG I2S1 on D3/D10/D4/D5
const stm32l4_sai_pins_t g_SAIPins = { GPIO_PIN_PB3_SAI1_SCK_B, GPIO_PIN_PB6_SAI1_FS_B, GPIO_PIN_PB5_SAI1_SD_B, GPIO_PIN_PB4_SAI1_MCLK_B };
const unsigned int g_SAIInstance = SAI_INSTANCE_SAI1B;
const unsigned int g_SAIMode = SAI_MODE_DMA;
static stm32l4_sai_t _SAI;

I2SClass I2S(&_SAI, g_SAIInstance, &g_SAIPins, STM32L4_SAI_IRQ_PRIORITY, g_SAIMode);

// ADC3101 on I2C3
ADC3101 adc3101(Wire1);

void processI2SData(uint8_t *data, size_t size) {
  int16_t *data16 = (int16_t *)data;

  // Copy first channel into model inputs
  for (size_t i = 0; i < size / 4 && sample_i + i < MODEL_INPUT_SAMPLES; i++, sample_i++) {
    inputs[0][sample_i] = data16[i * 2];
  }

  if (sample_i >= MODEL_INPUT_SAMPLES) {
    ready_for_inference = true;
  }
}

void onI2SReceive() {
  static uint8_t data[I2S_BUFFER_SIZE];
  size_t size = I2S.available();

  if (size > 0) {
    I2S.read(data, size);
    processI2SData(data, size);
  }
}

void setup() {
  Serial.begin(921600);

  while (!Serial && millis() < 500);

  pinMode(PIN_LED, OUTPUT);

  // For RFThing-DKAIoT
  /*
  pinMode(LS_GPS_ENABLE, OUTPUT);
  digitalWrite(LS_GPS_ENABLE, LOW);
  pinMode(LS_GPS_V_BCKP, OUTPUT);
  digitalWrite(LS_GPS_V_BCKP, LOW);
  pinMode(SD_ON_OFF, OUTPUT);
  digitalWrite(SD_ON_OFF, HIGH);
  */

  adc3101.setup();

  delay(500);

  // start I2S, MCLK enabled
  if (!I2S.begin(I2S_PHILIPS_MODE, I2S_SAMPLE_RATE, I2S_BITS_PER_SAMPLE, true)) {
    Serial.println("Failed to initialize I2S!");
    while (1); // do nothing
  }

  I2S.onReceive(onI2SReceive);

  // Trigger a read to start DMA
  I2S.peek();
  
  //Serial.println("Initializing DONE");
}

void loop() {
  if (ready_for_inference) {
    // Input buffer full, perform inference

    // Turn LED on during preprocessing/prediction
    digitalWrite(PIN_LED, HIGH);

    // Start timer
    long long t_start = millis();

    // Send signed 16-bit PCM little endian 1 channel
    //Serial.write((uint8_t*)inputs[0], MODEL_INPUT_SAMPLES*2);

    // Predict
    cnn(inputs, outputs);

    // Get output class
    unsigned int label = 0;
    number_t max_val = outputs[0];
    for (unsigned int i = 1; i < MODEL_OUTPUT_SAMPLES; i++) {
      if (max_val < outputs[i]) {
        max_val = outputs[i];
        label = i;
      }
    }

    static char msg[32];
    snprintf(msg, sizeof(msg), "%d,%d,%d", label, max_val, (int)(millis() - t_start));
    Serial.println(msg);

    // Turn LED off after prediction has been sent
    digitalWrite(PIN_LED, LOW);
    
    ready_for_inference = false;
    sample_i = 0;
  }
}
