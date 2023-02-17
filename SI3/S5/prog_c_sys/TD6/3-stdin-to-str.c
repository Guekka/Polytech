#include <stdio.h>

#define ELEMENT_TYPE unsigned char

#include "vector.h"

unsigned char *readline()
{
    vector vec = vector_make();
    int c      = EOF;
    while ((c = getchar()) != EOF)
    {
        vector_push_back(&vec, c);
    }
    if (c != EOF)
        vector_push_back(&vec, '\0');
    return vec.len > 0 ? vec.data : NULL;
}

int main(void)
{
    unsigned char *s = NULL;
    do
    {
        printf("Entrer une chaîne: ");
        fflush(stdout);
        s = readline();
        if (s)
        {
            printf("Chaîne lue : '%s'\n", s);
            free(s);
        }
    } while (s != NULL);
    return 0;
}