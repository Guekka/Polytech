#include <stdio.h>
#include <string.h>
#include <limits.h>

int main(void) {
    int array[CHAR_MAX];
    memset(array, 0, sizeof(int) * CHAR_MAX);

    int c;
    while ((c = getchar()) != EOF) {
        if (c >= CHAR_MAX) {
            printf("Ignored char: %d", c);
            continue;
        }
        array[c] += 1;
    }
    for (int character = 0; character < CHAR_MAX; ++character)
        printf("%c : %d\n", (char) character, array[character]);
}
