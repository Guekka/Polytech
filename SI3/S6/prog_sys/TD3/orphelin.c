#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
int main() {
    switch (fork()) {
        case -1:
            perror("fork");
            exit(1);
        case 0:
        {
            pid_t parent = getppid();
            while(parent == getppid()) {
                printf("I'm orphan :(\n");
                sleep(1);
            }
            printf("I've been adopted! by %d :)", getppid());
        }
        default:
        {
            // do nothing
        }
    }
}