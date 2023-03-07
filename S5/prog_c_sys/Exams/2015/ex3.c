/*******************************************************************************
Question 3 : Écrire fonction void rot13(char *s) qui applique un algo de 
chiffrement de texte de type César avec un décalage de 13.
Or un l'alphabet contient 26 lettres donc pour les 13 premieres à avance de 13 
dans la table ASCII et pour les 13 derniers on recule de 13.
*******************************************************************************/

#include <stdbool.h>
#include <stdio.h>
#include <string.h>

void rot13(char *str)
{
    for (char* c = str; *c; ++c)
    {
        // only changes lowercase letters
        if (*c < 'a' || *c > 'z')
            continue;
        
        if (*c - 'a' < 13)
            *c += 13;
        else
            *c -= 13;
    }
}

typedef struct expected 
{
    const char* data;
    const char* result;
} expected;

int main ()
{
  expected tests[2] =
  {
      { .data = "abcdefghijklmnopqrstuvwxyz", .result = "nopqrstuvwxyzabcdefghijklm" },
      { .data = "while true", .result = "juvyr gehr" }
  };
  for (int i = 0; i < 2; ++i)
  {
      char *dup = strdup(tests[i].data);
      rot13(dup);
      
      printf("%s\n", dup);
      
      if (strcmp(dup, tests[i].result) != 0)
        printf("Error: %i\n", i);
  }
}