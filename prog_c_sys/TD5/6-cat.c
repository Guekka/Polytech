#include <stdbool.h>
#include <stdio.h>
#include <string.h>

typedef struct options
{
    bool trailing_newline;
    bool interpret_backslash_escapes;
    bool help;
} options;

options parse_options(int argc, char **argv)
{
    options result = {.trailing_newline = true, .interpret_backslash_escapes = true, .help = false};

    for (int i = 1; i < argc; ++i)
    {
        const char *arg = argv[i];

        // first check for end of options
        if (strcmp(arg, "--") == 0)
            return result;

        // Now either we have a long argument..
        if (arg[0] == '-' && arg[1] == '-')
        {
            if (strcmp(arg, "--exit_with_help") == 0)
                result.help = true;
        }

        // ..one / several short ones..
        if (arg[0] == '-')
        {
            for (const char *opt = arg + 1; *opt; ++opt)
            {
                if (*opt == 'n')
                    result.trailing_newline = false;
                else if (*opt == 'E')
                    result.interpret_backslash_escapes = false;
                else if (*opt == 'h')
                    result.help = true;
            }
            continue;
        }
    }
    return result;
}

int main(int argc, char **argv)
{
    options opt = parse_options(argc, argv);
    if (opt.help)
    {
        printf("HELP ME");
        return 0;
    }

    int c = 0;
    while ((c = getchar()) != EOF)
    {
        putchar(c);
    }
    if (opt.trailing_newline)
        printf("\n");
}