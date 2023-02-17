#include <stdbool.h>
#include <stdio.h>
#include <string.h>
int main(int argc, char **argv)
{
    if (argc == 1)
        return 0;

    bool reverse = strcmp(argv[1], "-r") == 0;
    for (int i = 1; i < argc; ++i)
    {
        if (i == 1 && reverse)
            continue;

        const char *arg = argv[i];
        if (!reverse)
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

        putchar(' ');
    }
}