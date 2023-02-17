#include <stdint.h>
#include <assert.h>
#include <stdio.h>
#include <string.h>

int Strcmp(const uint8_t *s1, const uint8_t *s2) {
    while (*s1 && *s2) {
        if (*s1 > *s2)
            return 1;
        if (*s1 < *s2)
            return -1;
        ++s1;
        ++s2;
    }
    if (*s1 == 0 && *s2 == 0)
        return 0;
    if (*s1 == 0)
        return -1;
    return 1;
}

int Strstr(const uint8_t *haystack, const uint8_t *needle) {
    const uint8_t *end = haystack;
    while (*end)
        ++end;

    const int needle_len = strlen(needle);

    while (*haystack) {
        const int len = end - haystack;
        if (len < needle_len)
            return 0;

        if (*haystack == needle[0])
            return Strcmp(haystack, needle) == 0;
        ++haystack;
    }
}

int main(int argc, char *argv[]) {
    assert(Strstr("Test", "Test") == 1);
    assert(Strstr("Test", "est") == 1);
    assert(Strstr("Test", "Tes") == 1);
    assert(Strstr("Test", "Teste") == 0);
    assert(Strstr("Test", "Tesz") == 0);
    assert(Strstr("Test", "zest") == 0);

    const uint8_t *needle = argv[1];

    const char line[1024];
    while (fgets(line, 1024, stdin)) {
        if (Strstr(line, needle))
            printf("%s", line);
    }
}