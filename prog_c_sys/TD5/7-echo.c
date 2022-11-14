#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct
{
    char sep;    // valeur du spérateur ' ' par défaut et '\n' si option -O
    bool reverse; // 1 si option -r et 0 sinon.
} options;

void exit_with_help() {
    printf("Usage: echo [-r] [-O] [STRING]...\n");
    printf("Affiche les chaînes de caractères passées en paramètre\n");
    printf("Options:\n");
    printf("-r : affiche les chaînes dans l'ordre inverse\n");
    printf("-O : affiche les chaînes séparées par un retour à la ligne");
    exit(0);
}

char **option_analysis(char **argv, options *opt)
{
    opt->sep     = ' ';
    opt->reverse = 0;

    // skip program name
    ++argv;

    while (*argv != NULL)
    {
        const char *arg = *argv;

        // first check for end of options
        if (strcmp(arg, "--") == 0)
            break;

        // Now either we have a long argument..
        if (arg[0] == '-' && arg[1] == '-')
        {
            if (strcmp(arg, "--exit_with_help") == 0)
                exit_with_help();
        }

        // ..one / several short ones..
        if (arg[0] == '-')
        {
            for (const char *single_opt = arg + 1; *single_opt; ++single_opt)
            {
                if (*single_opt == 'O')
                    opt->sep = '\n';
                else if (*single_opt == 'r')
                    opt->reverse = true;
                else if (*single_opt == 'h')
                    exit_with_help();
            }
            ++argv;
            continue;
        }

        // ..or a non-option argument
        break;
    }
    return argv;
}

int main(int argc, char **argv)
{
    if (argc == 1)
        exit_with_help();

    options opt;
    for (argv = option_analysis(argv, &opt); *argv; ++argv)
    {
        const char *arg = *argv;
        if (!opt.reverse)
            printf("%s", arg);
        else
        {
            const char *end = arg;
            while (*end)
                ++end;
            while (end >= arg)
            {
                putchar(*end);
                --end;
            }
        }

        putchar(opt.sep);
    }
}