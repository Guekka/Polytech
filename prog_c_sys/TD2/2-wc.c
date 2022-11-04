#include <stdio.h>

int main(void) {
    int lines = 0;
    int words = 0;
    int chars = 0;
    
    int c = 0;
    while ((c = getchar()) != EOF) {
        if (c == '\n')
            ++lines;
        else if (c == ' ')
            ++words;
        else
            ++chars;
    }
    printf("c: %d, w: %d, l: %d", chars, words, lines);
}
