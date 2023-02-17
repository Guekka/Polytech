#include <stdio.h>

int main(int argc, char **argv) {
    int count = 0;
    int input = 0;

    while (1) {
        scanf("%d", &input);
        if (input < 0)
            break;
        count++;
    }
    printf("Read %d", count);
}