#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define MAX_SIGINT 5

int sigint_count = 0;

void signal_handler(int sig) {
    printf("Signal %d caught by signal_handler\n", sig);
    if (sig == SIGINT) {
        if (++sigint_count >= MAX_SIGINT) {
            printf("Too many SIGINTs, exiting");
            exit(1);
        }
    }
    else {
        sigint_count = 0;
    }
}

int main() {
    // capture SIGSEGV and SIGINT using sigaction()
    struct sigaction sigact;
    sigset_t sigmask;
    sigemptyset(&sigmask);
    sigact.sa_mask = sigmask;
    sigact.sa_handler = signal_handler;
    sigaction(SIGSEGV, &sigact, NULL);
    sigaction(SIGINT, &sigact, NULL);

    printf("Sleeping for 5 seconds\n");
    sleep(5);
    printf("Done sleeping\n");

    while (1) {
        // do nothing
    }
}