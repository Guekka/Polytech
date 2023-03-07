#include <stdint.h>
#include <memory.h>
#include <assert.h>

int Strneq(const uint8_t *s1, const uint8_t *s2, size_t n) {
    while (*s1 && *s2 && n) {
        if (*s1 > *s2)
            return 0;
        if (*s1 < *s2)
            return 0;
        ++s1;
        ++s2;
        --n;
    }
    return 1;
}

void suppression(uint8_t *str, const uint8_t *suppr) {
    uint8_t *end = str;
    while (*end)
        ++end;

    const int suppr_len = strlen(suppr);

    while (*str) {
        if (Strneq(str, suppr, suppr_len) == 1) {
            memmove(str, str + suppr_len, end - str - suppr_len);
            end -= suppr_len;
            *end = 0;
        } else
            ++str;
    }
}

int main() {
    uint8_t s[] = "TestTest";
    suppression(s, "st");
    assert(strcmp(s, "TeTe") == 0);

    uint8_t s2[] = "Bonjour";
    suppression(s2, "Bonjour");
    assert(strcmp(s2, "") == 0);

}