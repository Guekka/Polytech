#include <assert.h>
#include <memory.h>
#include <stdlib.h>

typedef int (*mapper)(int);

void map(mapper map, int *tab, size_t len)
{
    for (size_t i = 0; i < len; ++i)
    {
        tab[i] = map(tab[i]);
    }
}

int carre(int a)
{
    return a * a;
}

int main()
{
    int tab[] = {1, 2, 3, 4, 5};
    map(carre, tab, 5);

    int expected[] = {1, 4, 9, 16, 25};
    assert(memcmp(expected, tab, 5) == 0);
}