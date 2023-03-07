/*******************************************************************************
Question 2 : Verificateur d'accolades ouvrantes et fermantes dans un code,
renvoie 0 si faux, 1 sinon.
Précisions :
- Pas d'accolades dans les commentaires du code à analyser.
- Si l'on a 3 accolades fermantes suivi de 3 accolades ouvrantes ça ne fonctionne pas.
*******************************************************************************/

#include <stdbool.h>
#include <stdio.h>

bool check_braces(const char *str)
{
    if(!str)
        return true;
    
    int counter = 0;
    while(*str)
    {
        if (*str == '}')
            --counter;
        
        if (counter < 0)
            return false;
            
        if (*str == '{')
            ++counter;
            
        ++str;
    }
    return counter == 0;
}

typedef struct expected 
{
    const char* data;
    bool result;
} expected;

int main ()
{
  expected tests[3] =
  {
      { .data = "int main () {}{", .result = false },
      { .data = "while true }{", .result = false },
      { .data = "{{{}}}", .result = true }
  };
  for (int i = 0; i < 3; ++i)
  {
      if (tests[i].result != check_braces(tests[i].data))
        printf("Error: %i\n", i);
  }
}