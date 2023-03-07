#include <assert.h>
#include <stdbool.h>

bool palindrome(const char *str) {
    const char *end = str;
    while (*end)
        ++end;    
    --end; // go back before null terminator
    
    while (*str && *str++ == *end--)
        ;
    return *str == '\0';
}

int main() {
    assert(palindrome("ressasser") == true);
    assert(palindrome("kayak") == true);
    assert(palindrome("X") == true);
    assert(palindrome("test") == false);
}
