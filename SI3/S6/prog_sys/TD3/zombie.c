#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
    switch (fork()) {
        case -1:
            perror("fork");
            exit(1);
        case 0:
            printf("I'm the child\n");
            exit(0);
        default:
            sleep(10); // son is a zombie for 10 seconds
    }
}