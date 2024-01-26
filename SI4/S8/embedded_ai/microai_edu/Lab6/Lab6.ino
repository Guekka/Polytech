#include <SPI.h>
#include <LibLacuna.h>

#ifndef REGION
#define REGION R_EU868
#endif

// Keys and device address are MSB
static byte networkKey[] = {0x6E, 0x9A, 0x52, 0x92, 0xB5, 0xB2, 0x2F, 0xFB, 0x13, 0x23, 0x5B, 0x10, 0x31, 0x40, 0x6F, 0x17};
static byte appKey[] = {0x17, 0x2C, 0x2D, 0xC6, 0x8C, 0xCE, 0x27, 0x40, 0xD6, 0x32, 0x71, 0xD4, 0xBA, 0x6C, 0x86, 0xBB};
static byte deviceAddress[] = {0x26, 0x0B, 0xF7, 0xDD};

static lsLoraWANParams loraWANParams;
static lsLoraTxParams txParams;

void setup() {
  Serial.begin(115200);

  // GPIO
  pinMode(LS_LED_BLUE, OUTPUT);
  pinMode(LS_GPS_ENABLE, OUTPUT);
  pinMode(LS_GPS_V_BCKP, OUTPUT);
  pinMode(SD_ON_OFF, OUTPUT);
  pinMode(LS_VERSION_ENABLE, OUTPUT);

  // Normal running
  digitalWrite(LS_GPS_ENABLE, HIGH);
  digitalWrite(LS_GPS_V_BCKP, HIGH);
  digitalWrite(SD_ON_OFF, HIGH);
  digitalWrite(LS_VERSION_ENABLE, HIGH);

  delay(100);

  randomSeed(analogRead(LS_VERSION_MEAS));
  delay(20);
  digitalWrite(LS_VERSION_ENABLE, LOW);

  // LoRa
  // SX1262 configuration for lacuna LS200 board
  lsSX126xConfig cfg;
  lsCreateDefaultSX126xConfig(&cfg, BOARD_VERSION);

  // Special configuration for DKAIoT Board
  cfg.nssPin = E22_NSS;                           //19
  cfg.resetPin = E22_NRST;                        //14
  cfg.antennaSwitchPin = E22_RXEN;                //1
  cfg.busyPin = E22_BUSY;                         //2
  cfg.dio1Pin = E22_DIO1;                         //39

  // Initialize SX1262
  int result = lsInitSX126x(&cfg, REGION);

  // LoRaWAN session parameters
  lsCreateDefaultLoraWANParams(&loraWANParams, networkKey, appKey, deviceAddress);
  loraWANParams.txPort = 1;
  loraWANParams.rxEnable = true;
 
  // transmission parameters for terrestrial LoRa
  lsCreateDefaultLoraTxParams(&txParams, REGION);
  txParams.spreadingFactor = lsLoraSpreadingFactor_7;
  txParams.frequency = 868100000;
  //txParams.power = 1;

  while (!Serial && millis() < 5000);
}

void loop() {
  static int counter = 0;
  
  digitalWrite(LS_LED_BLUE, HIGH);

  static unsigned char data[1];
  data[0] = counter; // Counter
  
  // Sending LoRa message 
  int lora_result  = lsSendLoraWAN(&loraWANParams, &txParams, data, sizeof(data));
  Serial.print("Sent LoRaWAN data: ");
  Serial.print(data[0]);
  Serial.print(", result: ");
  Serial.println(lora_result);
  
  digitalWrite(LS_LED_BLUE, LOW);

  counter++;
  delay(5000);
}
