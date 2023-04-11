#include <signal.h>
#include <stdio.h>
#include <stdlib.h>

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
    // capture SIGSEGV and SIGINT using signal()
    signal(SIGSEGV, signal_handler);
    signal(SIGINT, signal_handler);
    while (1) {
        // do nothing
    }
}