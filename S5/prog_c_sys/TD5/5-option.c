#include <stdbool.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char **argv)
{
    bool end_of_options = false;
    for (int i = 1; i < argc; ++i)
    {
        const char *arg = argv[i];

        // first check for end of options
        if (strcmp(arg, "--") == 0)
        {
            end_of_options = true;
            continue;
        }

        if (end_of_options)
        {
            printf("Argument: %s\n", arg);
            continue;
        }

        // Now either we have a long argument..
        if (arg[0] == '-' && arg[1] == '-')
        {
            printf("Option longue: %s\n", arg);
            continue;
        }

        // ..one / several short ones..
        if (arg[0] == '-')
        {
            for (const char *opt = arg + 1; *opt; ++opt)
                printf("Option courte: -%c\n", *opt);
            continue;
        }

        // or positional
        end_of_options = true;
        printf("Argument: %s\n", arg);
    }
}