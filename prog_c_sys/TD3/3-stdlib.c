#include <stdint.h>
#include <assert.h>

void Strcpy(uint8_t *dst, const uint8_t *src) {
    while (*src)
        *dst++ = *src++;
}

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

uint8_t Toupper(uint8_t c) {
    if (c >= 'a' && c <= 'z')
        return c - 32;
    return c;
}


void Strupper(uint8_t *s) {
    while (*s) {
        *s = Toupper(*s);
        ++s;
    }
}

int main() {
    uint8_t s1[] = "Test";
    uint8_t s2[] = "Test";
    assert(Strcmp(s1, s2) == 0);

    Strupper(s2);
    assert(Strcmp(s1, s2) == 1);

    Strcpy(s1, s2);
    assert(Strcmp(s1, s2) == 0);
}