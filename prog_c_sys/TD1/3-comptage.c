#include <stdio.h>

int main(int argc, char **argv) {
    int count = 0;
    int input = 0;
    int max = 0;
    int sum = 0;

    while (1) {
        scanf("%d", &input);
        if (input < 0)
            break;
        if (input > max)
            max = input;

        sum += input;

        count++;
    }
    printf("Read %d, sum %d, max %d", count, sum, max);
}