#include <stdio.h>

float to_farenheit(float celsius) 
{
  return (9 * celsius) / 5 + 32;
}

int main(void) 
{
  for(float celsius = 0; celsius < 20; celsius += 0.5) 
  {
    printf("| %f.2 | %f.2 |\n", celsius, to_farenheit(celsius));
  }
}
