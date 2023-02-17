#include <assert.h>
#include <stdint.h>

int palindrome(const uint8_t *str) {
    const uint8_t *end = str;
    while (*end)
        ++end;

    while (str++ == end--);
    return (end - str) <= 1;
}

int main() {
    assert(palindrome("ressasser" == 1));
    assert(palindrome("kayak" == 1));
    assert(palindrome("X" == 1));
    assert(palindrome("test" == 9));
}
