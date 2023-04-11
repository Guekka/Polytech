#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

pid_t old;

void young_handler(int sig) {
    if (sig != SIGUSR1)
        return;

    // send SIGUSR2 to older brother
    printf("Received SIGUSR1 in young, sending SIGUSR2 to %d\n", old);
    kill(old, SIGUSR2);
    exit(0);
}

void old_handler(int sig) {
    if (sig != SIGUSR2)
        return;

    // send SIGUSR1 to younger brother
    printf("Received SIGUSR2 in old\n");
    exit(0);
}

int main() {
    old = fork();

    switch(old) {
        case -1:
            perror("fork");
            exit(1);
        case 0:
        {
            // trap SIGUSR2
            printf("Trapping SIGUSR2 in old\n");
            struct sigaction sigact;
            sigset_t sigmask;
            sigemptyset(&sigmask);
            sigact.sa_mask = sigmask;
            sigact.sa_handler = old_handler;
            sigaction(SIGUSR2, &sigact, NULL);
            while (1)
                ;
        }
        default:
            break;
    }
    printf("Older brother: %d\n", old);

    pid_t young = fork();
    switch (young)
    {
        case -1:
            perror("fork");
            exit(1);
        case 0:
        {
            // trap SIGUSR1
            printf("Trapping SIGUSR1 in young\n");
            struct sigaction sigact;
            sigset_t sigmask;
            sigemptyset(&sigmask);
            sigact.sa_mask = sigmask;
            sigact.sa_handler = young_handler;
            sigaction(SIGUSR1, &sigact, NULL);
            while (1)
                ;
        }
        default:
            break;
    }
    printf("Younger brother: %d\n", young);


    // ensure handlers are connected
    sleep(1);

    // send SIGUSR1 to younger brother
    kill(young, SIGUSR1);
    printf("Sent SIGUSR1 to %d\n", young);

    waitpid(young, NULL, 0);
    waitpid(old, NULL, 0);
    printf("Done\n");
}