#include <memory.h>
#include <stdint.h>
#include <stdio.h>

void print_base_n(uint64_t n, uint64_t base) {
    char buf[64];
    int i = 0;
    while (n) {
        buf[i++] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"[n % base];
        n /= base;
    }
    while (i) {
        putchar(buf[--i]);
    }
    putchar('\n');
}

void in_binary(int n) {
    if(n == 0)
        return;

    in_binary(n / 2);
    putchar((n % 2) + '0');
}

void in_binary_bitwise(int n) {
    if(n == 0)
        return;

    in_binary_bitwise(n >> 1);
    putchar(((n & 1) == 1) + '0');
}



int main() {
    print_base_n(1234567890, 10);
    print_base_n(1234567890, 16);
    print_base_n(1234567890, 2);


    in_binary(1234567890);
    putchar('\n');
    in_binary(0b1010101010101010101010101010100);
    putchar('\n');
    in_binary_bitwise(0b1010101010101010101010101010100);
    return 0;
}