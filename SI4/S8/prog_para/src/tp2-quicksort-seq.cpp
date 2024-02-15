
#include <omp.h>
#include <sys/time.h>

#include <cstdio>
#include <cstdlib>
#include <cstring>

struct Tablo
{
    int *tab;
    int size;
};

void quicksort(struct Tablo *ta, int index_min, int index_max)
{
    int i{};
    int j{};
    int v{};
    int t{};

    if (index_max - index_min < 2)
        return;

    //partition
    v = ta->tab[index_min];
    i = index_min;
    j = index_max;
    for (;;)
    {
        while (++i < index_max && ta->tab[i] < v)
            ;
        while (--j >= index_min && ta->tab[j] > v)
            ;
        if (i >= j)
            break;
        t          = ta->tab[i];
        ta->tab[i] = ta->tab[j];
        ta->tab[j] = t;
    }
    t                  = ta->tab[i - 1];
    ta->tab[i - 1]     = ta->tab[index_min];
    ta->tab[index_min] = t;

    omp_set_nested(1);
    omp_set_max_active_levels(10);
#pragma omp parallel sections default(none) shared(ta, index_min, index_max, i)
    {
#pragma omp section
        quicksort(ta, index_min, i);
#pragma omp section
        quicksort(ta, i, index_max);
    }
}

void printArray(struct Tablo *tmp)
{
    printf("---- Array of size %i ---- \n", tmp->size);
    int size = tmp->size;
    int i;
    for (i = 0; i < size; ++i)
    {
        printf("%i ", tmp->tab[i]);
    }
    printf("\n");
}

void generateRandomArray(Tablo *s, int size)
{
    s->size = size;
    s->tab  = new int[size];
    for (int i = 0; i < size; i++)
        s->tab[i] = rand();
}

void generateSortedArray(struct Tablo *s, int size)
{
    s->size = size;
    s->tab  = new int[size];
    int i;
    for (i = 0; i < size; i++)
    {
        s->tab[i] = i;
    }
}

void generateReverseSortedArray(struct Tablo *s, int size)
{
    s->size = size;
    s->tab  = new int[size];
    int i;
    for (i = 0; i < size; i++)
    {
        s->tab[i] = size - i;
    }
}

int main(int argc, char **argv)
{
    int size = 10'000'000;
    if (argc > 1)
        size = atoi(argv[1]);
    Tablo tmp{};

    generateRandomArray(&tmp, size);
    // printArray(&tmp);

    timeval start, end;
    gettimeofday(&start, NULL);
    quicksort(&tmp, 0, tmp.size);
    gettimeofday(&end, NULL);

    fprintf(stderr,
            "(size,loop duration QS in micro sec):%d %ld \n",
            size,
            ((end.tv_sec * 1000000 + end.tv_usec) - (start.tv_sec * 1000000 + start.tv_usec)));
    // printArray(&tmp);
}

// sequential section: 12 899 424
// parallel section: 23 256 978
// parallel section with 4 levels : 17 716 821
// parallel section with 3 levels : 21 011 531
// parallel section with 10 levels: 11 881 809
