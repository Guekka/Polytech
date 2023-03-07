#include <malloc.h>
#include <stdio.h>

char *Strcpy(char *dest, const char *src)
{
    while (*src)
    {
        *dest = *src;
        ++src;
        ++dest;
    }
}

size_t Strlen(const char *s)
{
    size_t len = 0;
    while (*s)
    {
        ++s;
        ++len;
    }
}

char *Strdup(const char *s)
{
    char *dup = (char *) malloc(Strlen(s));
    Strcpy(dup, s);
    return dup;
}

char *Strchr(char *s, int c)
{
    while (*s && *s != c)
        ++s;

    return *s == c ? s : NULL;
}

int main() {}