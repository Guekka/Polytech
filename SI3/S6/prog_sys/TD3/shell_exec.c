#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>

int main() {
    switch (fork()) {
        case -1:
            goto error;
        case 0:
            exit(execle("/bin/who", "who", NULL, NULL));
        default:
        {
            int status;
            wait(&status);
            if (status != 0)
            {
                printf("Error: %d\n", WEXITSTATUS(status));
            }
        }
    }

    // exec("cd"); does not work. Instead, use chdir
    chdir(getenv("HOME"));

    switch (fork()) {
        case -1:
            goto error;
        case 0:
            exit(execle("/bin/ls", "ls", "-ls", NULL, NULL));
        default:
        {
            int status;
            wait(&status);
            if (status != 0)
            {
                printf("Error: %d\n", WEXITSTATUS(status));
            }        }
    }

    return 0;

error:
    perror("fork");
    exit(1);
}