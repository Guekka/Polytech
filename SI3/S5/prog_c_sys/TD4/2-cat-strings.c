#include <stdarg.h>
#include <stdio.h>

void cat_strings(char str1[], ...) {
    va_list args;

    const char *the_arg = str1;
    va_start(args, str1);
    while (the_arg) {
        printf("%s", the_arg);
        the_arg = va_arg(args, char *);
    }
    va_end(args);
}

int main() {
    cat_strings("es", "sa", "i", NULL);
}