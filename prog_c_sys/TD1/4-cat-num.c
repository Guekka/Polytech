#include <stdio.h>

int main() {
    int read_char = 0;
    int line_counter = 1;
    printf("%d ", line_counter);
    while ((read_char = getchar()) != EOF) {
        printf("%c", read_char);
        if (read_char == '\n')
            printf("%d ", ++line_counter);
    }
}
