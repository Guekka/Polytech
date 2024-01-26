#include <ICM_20948.h>                      // https://github.com/sparkfun/SparkFun_ICM-20948_ArduinoLibrary

#define SAMPLE_RATE 20

// ICM
ICM_20948_I2C icm;

static long long timer = 0;

void setup() {
  // 10-14-21: Mandatory on new board revision otherwise I2C does not work
  pinMode(SD_ON_OFF, OUTPUT);
  digitalWrite(SD_ON_OFF, HIGH);

  // Initialize pin for blinking LED
  pinMode(LS_LED_BLUE, OUTPUT);

  // Initialize serial port
  Serial.begin(115200);

  // Wait for initialization
  while (!Serial && millis() < 5000);

  // Initialize I2C used by IMU
  Wire.begin();

  // Initialize IMU
  icm.begin(Wire, 0);

  // Set sample rate to ~20Hz
  icm.setSampleRate((ICM_20948_Internal_Acc | ICM_20948_Internal_Gyr), {56, 55}); // a = 56 -> 20.09Hz, g = 55 -> 20Hz 

  // Notify readyness
  Serial.println("READY");

  timer = millis();
}

void loop() {
  // Try to respect sampling rate
  if (millis() > timer + (1000 / SAMPLE_RATE)) {
    timer = millis();

    if (icm.dataReady()) {
      // Read accelerometer data
      icm.getAGMT(); // The values are only updated when you call 'getAGMT'
    }
    
    // Blink LED for activity indicator
    digitalWrite(LS_LED_BLUE, 1 - digitalRead(LS_LED_BLUE)); 

    // Format message with accelerometer data
    char msg[128];
    snprintf(msg, sizeof(msg), "0,%f,%f,%f\r\n", icm.accX() / 1000.0f, icm.accY() / 1000.0f, icm.accZ() / 1000.0f);

    // Send message over serial port
    Serial.print(msg);
  }
}
