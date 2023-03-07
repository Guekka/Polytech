#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>

#define COUNT 10

pid_t create_son(int i) {
    int pid = fork();
    switch (pid)
    {
        case -1:
            perror("fork");
            exit(1);
        case 0:
            for(int j = 0; j < COUNT; j++)
            {
                printf("%d\n", i);
                sleep(1);
            }
            exit(0);
        default:
            return pid;
    }
}

int main() {
    pid_t pids[COUNT] = {};
    for(int i = 0; i < COUNT; ++i) {
        pids[i] = create_son(i);
    }

    for(int i = 0; i < COUNT; ++i) {
        waitpid(pids[i], NULL, 0);
    }
}