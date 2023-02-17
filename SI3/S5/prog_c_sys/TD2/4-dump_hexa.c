#include <stdio.h>
#include <memory.h>
#include <wchar.h>
#include <locale.h>

const int CHARS_PER_LINE = 16;

void dump_line(char line[CHARS_PER_LINE], int line_len) {
    setlocale(LC_ALL, "");
//fwide(stdout, 1);

    for (int i = 0; i < CHARS_PER_LINE; ++i)
        printf("%02x ", (unsigned char) line[i]);
    printf(" | ");
    for (int i = 0; i < line_len; ++i)
        if (line[i] >= 32 && line[i] <= 126)
            printf("%c", line[i]);
        else
            printf(".");
    printf("\n");
}

int main() {
    // dump hexa of stdin by lines of 16

    char buffer[CHARS_PER_LINE];

    int char_counter = 0;
    int line_counter = 0;
    int c;
    while ((c = getchar()) != EOF) {
        if (char_counter == 0)
            printf("%08i: ", line_counter++);
        buffer[++char_counter] = c;
        if (char_counter == 16) {
            dump_line(buffer, char_counter);
            char_counter = 0;
            memset(buffer, 0, CHARS_PER_LINE);
        }
    }
    // last line
    if (char_counter != 0)
        dump_line(buffer, char_counter);

}