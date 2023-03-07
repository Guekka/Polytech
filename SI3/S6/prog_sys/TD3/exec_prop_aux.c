#include <stdio.h>
#include <unistd.h>
int main() {
    printf("\nThe parent buffer was lost\n");
    printf("But the pid is still the same: %d\n", getpid());
}