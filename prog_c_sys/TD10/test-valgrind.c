#include <stdio.h>
#include <stdlib.h>

void error1(void) {  // Accès interdit en écriture
  int *p = malloc(3 * sizeof(*p));
  if (p != NULL) {
    p[3] = 0;
    free(p);
  }
}

void error2(void) {  // Accès interdit en écriture
  int *p = malloc(sizeof(*p));
  if (p != NULL) {
    free(p);
    *p = 1;
  }
}

void error3(void) { // Fuite de mémoire
  int *p = malloc(sizeof(*p));
  if (p != NULL)
    free(p);
  p = malloc(sizeof(*p));
  p = NULL;
}

void error4(void) { // Valeur non initialisée (1)
  int j;
  if (j) { // Ceci est détecté par le compilateur
    printf("Hello!\n");
  }
}

void error5(void) {  // Valeur non initialisée (1)
  int *i = malloc(sizeof(*i));
  if (*i) {
    printf("Hello!\n");
  }
}

int main(void) {
  error1();
  error2();
  error3();
  error4();
  error5();
}