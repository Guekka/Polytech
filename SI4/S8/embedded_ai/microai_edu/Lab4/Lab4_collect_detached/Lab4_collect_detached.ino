#include <ICM_20948.h>                      // https://github.com/sparkfun/SparkFun_ICM-20948_ArduinoLibrary

#define SAMPLE_RATE 20

// ICM
ICM_20948_I2C icm;

static long long timer = 0;

#define DURATION 5 * 60
#define ACCEL_SAMPLES DURATION * SAMPLE_RATE
static float accel_buf[ACCEL_SAMPLES][3];
static size_t sample_i = 0;

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
  if (sample_i < ACCEL_SAMPLES) {
    // Try to respect sampling rate
    if (millis() > timer + (1000 / SAMPLE_RATE)) {
      timer = millis();

      if (icm.dataReady()) {
        // Read accelerometer data
        icm.getAGMT(); // The values are only updated when you call 'getAGMT'
      }
  
      // Blink LED for activity indicator
      digitalWrite(LS_LED_BLUE, 1 - digitalRead(LS_LED_BLUE));
  
      // Read accelerometer data
      accel_buf[sample_i][0] = icm.accX() / 1000.0f; 
      accel_buf[sample_i][1] = icm.accY() / 1000.0f; 
      accel_buf[sample_i][2] = icm.accZ() / 1000.0f; 
  
      sample_i++;
    }
  } else { // Buffer is full, send to host
    char msg[128];

    // LED lights up while waiting for host
    digitalWrite(LS_LED_BLUE, HIGH); 

    // Wait for host to be available to read
    while (!Serial.println("READY")) delay(100); 
    do {
      int res = Serial.readBytesUntil('\n', msg, sizeof(msg));
    } while(strncmp(msg, "READY", 5));

    // Send data to the host
    for (int i = 0; i < sample_i; i++) {
      // Blink LED for activity indicator
      digitalWrite(LS_LED_BLUE, 1 - digitalRead(LS_LED_BLUE));
      
      // Simulate timestamp, avoids using additional memory for each sample in the buffer
      int ts =  i * 1000 / SAMPLE_RATE;
      
      // Format message with accelerometer data
      snprintf(msg, sizeof(msg), "3,%d,%f,%f,%f\r\n", ts, accel_buf[i][0], accel_buf[i][1], accel_buf[i][2]);
  
      // Send message over serial port
      Serial.print(msg);
    }

    // LED is off once transmission is finished
    digitalWrite(LS_LED_BLUE, LOW); 

    while (true) delay(10000); // Wait until reset
    // sample_i = 0; // If we want to go back to capturing immediately
  }
}
