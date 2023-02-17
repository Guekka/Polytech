#include <stdio.h>
#include <string.h>

void xorcrypt(unsigned char msg[], unsigned char key[]) {
    int key_idx = 0;
    const int key_len = strlen(key);
    while (*msg != '\0') {
        *msg ^= key[key_idx];
        if(++key_idx == key_len)
            key_idx = 0;
        ++msg;
    }
}

int main() {
    unsigned char msg[] = "HELLO, WORLD";
    unsigned char key[] = "abcde";
    xorcrypt(msg, key);
    printf("%s", msg);
    return 0;
}
