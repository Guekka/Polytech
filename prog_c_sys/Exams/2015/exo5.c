/*******************************************************************************
Question 5 : On appelle nombre de Harshad un nombre divisible par la somme de ses 
chiffres. Par exemple : 12 est divisble par 3 (= 1+2).
Ecrivez la fonction harshad qui vérifie si l'entier passé en paramètre est un 
nombre de harshad ou pas.
*******************************************************************************/

#include <stdbool.h>
#include <stdio.h>

bool harshad(int x)
{
    int number = x;
    int digit_sum = 0;
    while (x)
    {
        digit_sum += x % 10;
        x /= 10;
    }
    return (number % digit_sum) == 0;
}


typedef struct expected 
{
    const int data;
    const bool result;
} expected;

int main ()
{
  expected tests[4] =
  {
      { .data = 12, .result = true },
      { .data = 13, .result = false },
      { .data = 24, .result = true },
      { .data = 29, .result = false },
  };
  for (int i = 0; i < 4; ++i)
  {
      if (harshad(tests[i].data) != tests[i].result)
        printf("Error: %i\n", i);
  }
}