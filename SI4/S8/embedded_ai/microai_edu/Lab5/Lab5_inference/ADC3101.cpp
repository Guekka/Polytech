#include "ADC3101.h"

ADC3101::ADC3101(TwoWire &i2c, uint8_t address, bool debug) : i2c(i2c), address(address), debug(debug) {
}

void ADC3101::writeI2C(int reg, int val) {
  int ret = 0;
  i2c.beginTransmission(address);
  i2c.write(reg);
  if (val != -1) {
    i2c.write(val);
  }
  ret = i2c.endTransmission();
  if (debug) Serial.println(ret);
}

int ADC3101::readI2C() {
    int ret = 0;
    ret = i2c.requestFrom(address, 1);          // Tell slave we need to read 1byte from the current register
    if (debug) Serial.println(ret);
    return i2c.read();
}

void ADC3101::setup() {
  int ret = 0;

  i2c.begin();
  if (debug) Serial.println("1. Define starting point:");
  if (debug) Serial.println("Set register page to 0");
  writeI2C(0x00, 0x00);

  if (debug) Serial.println("Initiate software reset");
  writeI2C(0x01, 0x01);

  if (debug) Serial.println("2. Program Clock Settings");
  if (debug) Serial.println("ADC_CLKIN = MCLK, P=1, R=1, J=4, D=0000");
  writeI2C(0x04, 0x00); // CODEC_CLKIN = MCLK
  //writeI2C(0x04, 0b00000011); // CODEC_CLKIN = PLL_CLK
  writeI2C(0x05, 0x11); // PLL power down, P=1, R=1
  //writeI2C(0x05, 0b10010001); // PLL power up, P=1, R=1
  writeI2C(0x06, 0x04);
  writeI2C(0x07, 0x00);
  writeI2C(0x08, 0x00);

  if (debug) Serial.println("Program and power up NADC: NADC = 1, divider powered on");
  writeI2C(0x12, 0x81);

  if (debug) Serial.println("Program and power up MADC: MADC = 1, divider powered on");
  //writeI2C(0x13, 0x81); //MADC=1
  writeI2C(0x13, 0x82); //MADC=2
  //writeI2C(0x13, 0x83); //MADC=2

  if (debug) Serial.println("Program OSR value: AOSR = 128");
  //writeI2C(0x14, 0x00); //256
  writeI2C(0x14, 0x80); //128
  //writeI2C(0x14, 0x20);

  if (debug) Serial.println("Program I2S word length as required: mode is i2s, wordlength is 16, slave mode (default)");
  writeI2C(0x1B, 0x00);

  if (debug) Serial.println("Program the processing block to be used: PRB_P1");
  writeI2C(0x3D, 0x01);

  if (debug) Serial.println("3. Program Analog Blocks");
  if (debug) Serial.println("Set register Page to 1");
  writeI2C(0x00, 0x01);

  if (debug) Serial.println("Program MICBIAS if applicable: MICBIAS1 = 3.3V, MICBIAS2 = 3.3V");
  //writeI2C(0x33, 0b01010000); //2.5V
  writeI2C(0x33, 0b01111000); //3.3V

  //writeI2C(0x33, 0b00101000); //2V
  //writeI2C(0x33, 0b00000000);

  if (debug) Serial.println("Program MicPGA");
  if (debug) Serial.println("Left Analog PGA Setting = 40dB");
  //writeI2C(0x3b, 0b00010000);
  //writeI2C(0x3b, 0b00000000);
  //writeI2C(0x3b, 0b00001000);
  writeI2C(0x3b, 0b01010000); //40dB
  //writeI2C(0x3b, 0b01001000); //36dB

  if (debug) Serial.println("Right Analog PGA Setting = 40dB");
  //writeI2C(0x3c, 0b00010000);
  //writeI2C(0x3c, 0b00001000);
  //writeI2C(0x3c, 0b00000000);
  writeI2C(0x3c, 0b01010000); //40dB
  //writeI2C(0x3c, 0b01001000); //36dB

  if (debug) Serial.println("Routing of inputs/common mode to ADC input");
  if (debug) Serial.println("Unmute analog PGAs and set analog gain");
  if (debug) Serial.println("Left ADC Input selection for Left PGA = IN1L(P) as Single-Ended");
  writeI2C(0x34, 0b00111111);
  //writeI2C(0x34, 0b11111111);

  if (debug) Serial.println("Right ADC Input selection for Right PGA = IN1R(M) as Single-Ended");
  writeI2C(0x37, 0b00111111);
  //writeI2C(0x37, 0b11111111);

  //Route right channel to left PGA
  //writeI2C(0x36, 0b00110011);
  //Route left channel to right PGA
  //writeI2C(0x39, 0b00110011);

  //Bypass PGA
  //writeI2C(0x36, 0b11111111); // Left
  //writeI2C(0x39, 0b11111111); // Right


  if (debug) Serial.println("4. Program ADC");
  if (debug) Serial.println("Set register Page to 0");
  writeI2C(0x00, 0x00);

  if (debug) Serial.println("Power up ADC channels");
  writeI2C(0x51, 0xc2);
  //writeI2C(0x51, 0b11000000);
  //writeI2C(0x51, 0b01000010);
  //writeI2C(0x51, 0x82); // left channel
  //writeI2C(0x51, 0x42); // right channel
  
  if (debug) Serial.println("Unmute digital volume control and set gain = 0 dB");
  //writeI2C(0x52, 0x00);
  //writeI2C(0x52, 0b10000000); // MUTE LEFT, UNMUTE RIGHT
  writeI2C(0x52, 0b00000000);
  //writeI2C(0x52, 0b00001000); // MUTE RIGHT, UNMUTE RIGHT

  if (debug) Serial.println("Set left ADC volume control = 5 dB");
  //writeI2C(0x53, 0b00101000); // 20dB
  //writeI2C(0x53, 0b00010100); // 10dB
  writeI2C(0x53, 0b00001010); // 5dB

  if (debug) Serial.println("Set right ADC volume control = 5 dB");
  //writeI2C(0x54, 0b00101000); // 20dB
  //writeI2C(0x54, 0b00010100); // 10dB
  writeI2C(0x54, 0b00001010); // 5dB


  if (debug) Serial.println("5. Program filters");
  if (debug) Serial.println("Set register Page to 4");
  writeI2C(0x00, 0x04);

  // 15Hz high-pass Butterworth 1st order 0dB
  // N0=0x7F9E
  // N1=0x8062
  // D1=0x7F3E

  // 30Hz high-pass Butterworth 1st order 0dB
  // N0=0x7F3F
  // N1=0x80C1
  // D1=0x7E7F

  if (debug) Serial.println("Coefficient N0(15:8) for Left ADC programmable first-order IIR");
  //writeI2C(8, 0b01111111); // Flat 0dB
  //writeI2C(8, 0x7F); // 15Hz high-pass Butterworth 1st order 0dB
  writeI2C(8, 0x7F); // 30Hz high-pass Butterworth 1st order 0dB

  if (debug) Serial.println("Coefficient N0(7:0) for Left ADC programmable first-order IIR");
  //writeI2C(9, 0b11111111); // Flat 0dB
  //writeI2C(9, 0x9E); // 15Hz high-pass Butterworth 1st order 0dB
  writeI2C(9, 0x3F); // 30Hz high-pass Butterworth 1st order 0dB

  // N1 = 0
  if (debug) Serial.println("Coefficient N1(15:8) for Left ADC programmable first-order IIR");
  //writeI2C(10, 0b00000000); // Flat 0dB
  //writeI2C(10, 0x80); // 15Hz high-pass Butterworth 1st order 0dB
  writeI2C(10, 0x80); // 30Hz high-pass Butterworth 1st order 0dB

  if (debug) Serial.println("Coefficient N1(7:0) for Left ADC programmable first-order IIR");
  //writeI2C(11, 0b00000000); // Flat 0dB
  //writeI2C(11, 0x62); // 15Hz high-pass Butterworth 1st order 0dB
  writeI2C(11, 0xC1); // 30Hz high-pass Butterworth 1st order 0dB

  // D1 = 0
  if (debug) Serial.println("Coefficient D1(15:8) for Left ADC programmable first-order IIR");
  //writeI2C(12, 0b00000000); // Flat 0dB
  //writeI2C(12, 0x7F); // 15Hz high-pass Butterworth 1st order 0dB
  writeI2C(12, 0x7E); // 30Hz high-pass Butterworth 1st order 0dB

  if (debug) Serial.println("Coefficient D1(7:0) for Left ADC programmable first-order II");
  //writeI2C(13, 0b00000000); // Flat 0dB
  //writeI2C(13, 0x3E); // 15Hz high-pass Butterworth 1st order 0dB
  writeI2C(13, 0x7F); // 30Hz high-pass Butterworth 1st order 0dB
}
