#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <wait.h>

char *readline_or_exit() {
    char *line = NULL;
    size_t len = 0;
    if ((getline(&line, &len, stdin)) == -1) {
        exit(EXIT_SUCCESS);
    }
    // removes the newline character
    line[strlen(line) - 1] = '\0';
    return line;
}

void cd(const char *arg) {
    // "cd" with no argument is equivalent to "cd $HOME"
    if (*arg == '\0') {
        arg = getenv("HOME");
    }
    if (chdir(arg) == -1) {
        perror("chdir");
    }
}

void my_system(char* line) {
    switch (fork()) {
        case -1:
            perror("fork");
            exit(1);
        case 0:
        {
            char *argv[100] = {};
            int i = 0;
            char *token = strtok(line, " ");
            while (token != NULL) {
                argv[i++] = token;
                token = strtok(NULL, " ");
                if (i == 99) {
                    perror("Too many arguments");
                    exit(1);
                }
            }
            argv[i] = NULL;

            execvp(argv[0], argv);
            perror("execvp");
            exit(1);
        }
        default:
        {
            wait(NULL);
        }
    }
}

void shell_exec(char *line) {
    if (strncmp(line, "cd", 2) == 0)
    {
        cd(line + 3);
    } else {
        my_system(line);
    }
}

int main() {
    while (1) {
        printf("shell> ");
        char *line = readline_or_exit();
        shell_exec(line);
        free(line);
    }
}