#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

void with_fork() {
    printf("fork:\n");
    printf("This string was printed by the parent process for fork");
    pid_t pid = fork();
    switch (pid) {
        case -1:
            perror("fork");
            exit(1);
        case 0:
            printf("\nAnd this one by the child process\n");
            fflush(stdout);
            exit(0);
        default:
            waitpid(pid, NULL, 0);
    }
    sleep(3);
    printf("\n\n");
}


void with_exec() {
    printf("exec:\n");
    printf("Current pid: %d\n", getpid());
    printf("This string was printed by the parent process for exec");
    execlp("./exec_prop_aux", "exec_prop_aux", "coucou", NULL);
    printf("This string will never be printed");
}

int main() {
    with_fork();
    with_exec();
}