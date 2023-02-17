#include <memory.h>
#include <stdint.h>
#include <assert.h>

void suppress_char(uint8_t *str, uint8_t c) {
    uint8_t *end = str;
    while (*end)
        ++end;

    while (*str) {
        if (*str == c) {
            memmove(str, str + 1, end - str - 1);
            --end;
            *end = 0;
        } else
            ++str;
    }
}

int main() {
    uint8_t s[] = "TestTest";
    suppress_char(s, 'T');
    assert(strcmp(s, "estest") == 0);

    suppress_char(s, 'e');
    assert(strcmp(s, "stst") == 0);

    suppress_char(s, 's');
    assert(strcmp(s, "tt") == 0);

    suppress_char(s, 't');
    assert(strcmp(s, "") == 0);

    suppress_char(s, 't');
    assert(strcmp(s, "") == 0);
}