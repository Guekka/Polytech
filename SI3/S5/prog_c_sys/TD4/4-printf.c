#include <stdarg.h>
#include <stdio.h>


void Printf(const char *format, ...) {
    va_list args;
    va_start(args, format);

    char specifier = 0;
    while (*format) {
        if (*format == '%') {
            specifier = *(++format);
        }
        switch (specifier) {
            case 'd': {
                char buffer[50] = {0};
                const int len = sprintf(buffer, "%d", va_arg(args, int));
                for (int i = 0; i < len; ++i)
                    putchar(buffer[i]);
                break;
            }
            case 'x': {
                char buffer[50] = {0};
                const int len = sprintf(buffer, "%x", va_arg(args, int));
                for (int i = 0; i < len; ++i)
                    putchar(buffer[i]);
                break;
            }
            case 'f': {
                char buffer[50] = {0};
                const int len = sprintf(buffer, "%f", va_arg(args, double));
                for (int i = 0; i < len; ++i)
                    putchar(buffer[i]);
                break;
            }
            case 'c': {
                char val = (char) va_arg(args, int);
                putchar(val);
                break;
            }
            default: {
                putchar(*format);
                break;
            }
        }
        specifier = 0;
        ++format;
    }
    va_end(args);
}

int main() {
    Printf("Hello. I'm %d yo and I love the number %f. My favorite letter is %c\nOh, and I love hex: %x\n", 20, 59.9,
           'd',
           14);
}