/*******************************************************************************
Question 1  Écrire la fonction void shuffle(char *str) qui mélange les caractères
de la chaîne str par tirage au sort à l'aide de la fonction rand().
*******************************************************************************/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

/// from https://stackoverflow.com/a/6852396
/// Assumes 0 <= max <= RAND_MAX
/// Returns in the closed interval [0, max]
long random_at_most(long max) {
  // max <= RAND_MAX < ULONG_MAX, so this is okay.
  unsigned long
    num_bins = (unsigned long) max + 1,
    num_rand = (unsigned long) RAND_MAX + 1,
    bin_size = num_rand / num_bins,
    defect   = num_rand % num_bins;

  long x;
  do {
   x = random();
  }
  // This is carefully written not to overflow
  while (num_rand - defect <= (unsigned long)x);

  // Truncated division is intentional
  return x/bin_size;
}

void swap(char *lhs, char *rhs) 
{
    char tmp = *rhs;
    *rhs = *lhs;
    *lhs = tmp;
}

void shuffle(char *str) {
    int size = strlen(str);
    --size; // don't shuffle null terminator
    
    while (size > 0)
    {
        int idx = random_at_most(size);
        swap(str + size, str + idx);
        --size;
    }
}

int main()
{
    srand(time(NULL));
    const char chaine[] = "chaine";
    for(int _i = 0; _i < 5; ++_i) {
        char *dup = strdup(chaine);   
        shuffle(dup);
        printf("%s\n", dup);
    }
}