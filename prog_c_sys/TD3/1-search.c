#include <assert.h>
#include <stdint.h>

int indice(const uint8_t *str, const uint8_t c) {
    const uint8_t *ptr = str;
    while (*ptr && *ptr != c)
        ++ptr;
    return *ptr == c ? ptr - str : -1;
}

int indice_droite(const uint8_t *str, const uint8_t c) {
    const uint8_t *ptr = str;
    while (*ptr)
        ++ptr;
    while (ptr != str && *ptr != c)
        --ptr;
    return *ptr == c ? ptr - str : -1;
}

int main() {
    assert(indice("Test", 'T') == 0);
    assert(indice("Test", 't') == 3);
    assert(indice("Test", 'z') == -1);
    assert(indice("Tester", 'e') == 1);
    assert(indice_droite("Test", 'T') == 0);
    assert(indice_droite("Test", 't') == 3);
    assert(indice_droite("Test", 'z') == -1);
    assert(indice_droite("Tester", 'e') == 4);
}
