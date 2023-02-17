#include <assert.h>
#include <stdlib.h>

typedef int (*predicate)(int, int);

int iterate(predicate pred, const int *tab, size_t len)
{
    int res = tab[0];
    for (size_t i = 1; i < len; ++i)
    {
        res = pred(res, tab[i]);
    }
    return res;
}

int max2(int a, int b)
{
    return a > b ? a : b;
}

int max(const int *tab, size_t len)
{
    return iterate(max2, tab, len);
}

int sum2(int a, int b)
{
    return a + b;
}

int sum(const int *tab, size_t len)
{
    return iterate(sum2, tab, len);
}

int prod2(int a, int b)
{
    return a * b;
}

int prod(const int *tab, size_t len)
{
    return iterate(prod2, tab, len);
}

int main()
{
    const int tab[] = {1, 2, 3, 4, 5};

    assert(max(tab, 5) == 5);
    assert(sum(tab, 5) == 15);
    assert(prod(tab, 5) == 120);
}