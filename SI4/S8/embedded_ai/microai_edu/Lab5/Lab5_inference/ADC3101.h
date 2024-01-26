#ifndef _ADC3101_H_
#define _ADC3101_H_

#include <Wire.h>

#define ADC3101_ADDR00 0x18
#define ADC3101_ADDR01 0x19
#define ADC3101_ADDR10 0x1a
#define ADC3101_ADDR11 0x1b

class ADC3101 {
private:
  TwoWire &i2c;
  uint8_t address;
  bool debug;

public:
  ADC3101(TwoWire &i2c, uint8_t address = ADC3101_ADDR00, bool debug = false);

  void writeI2C(int reg, int val = -1);
  int readI2C();
  void setup();
};

#endif//_ADC3101_H_
