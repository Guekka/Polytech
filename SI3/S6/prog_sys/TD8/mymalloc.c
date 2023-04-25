/*
 * my-malloc.c  -- Implementation de malloc, free, calloc, realloc
 *
 * Implémentation first-fit pour malloc
 *
 * Erick Gallesio (2020/04/10)
 * Stéphane Lavirotte (2023/04/13)
 */

#include "mymalloc.h"

#include "obfuscate.h"

#include <assert.h>
#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

// ======================================================================
//
//    Définition des fonctions. NE PAS MODIFIER CETTE PREMIERE PARTIE.
//
// ======================================================================

// Les fonctions internes à implémenter
void *internal_malloc(size_t size);
void internal_free(void *data);
void *internal_calloc(size_t nmemb, size_t size);
void *internal_realloc(void *ptr, size_t size);

// Les fonctions externes : ces fonctions appellent les fonctions internes
//  que vous avez à écrire en y ajoutant une trace qui est utilisée par
//  to-html et villoc.py pour générer les .html
void *mysbrk(intptr_t increment)
{
    void *ret, *old, *tmp;
    static int first_call = 1;

    if (first_call)
    {
        old = sbrk(0);
        fprintf(stderr, "SYS_brk(0)                    =  %p\n", old);
        first_call = 0;
    }

    ret = sbrk(increment);
    tmp = ret + increment;
    fprintf(stderr, "SYS_brk(%p)                    = %p\n", tmp, tmp);
    return ret;
}

void *mymalloc(size_t size)
{
    void *ret = internal_malloc(size);
    fprintf(stderr, "mymalloc(%ld)                     = %p\n", size, ret);
    return ret;
}

void myfree(void *ptr)
{
    internal_free(ptr);
    fprintf(stderr, "myfree(%p)                    = <void>\n", ptr);
}

void *myrealloc(void *ptr, size_t size)
{
    void *ret = internal_realloc(ptr, size);
    fprintf(stderr, "myrealloc(%p, %ld)                     = %p\n", ptr, size, ret);
    return ret;
}

void *mycalloc(size_t nmemb, size_t size)
{
    void *ret = internal_calloc(nmemb, size);
    fprintf(stderr, "mycalloc(%ld, %ld)                   = %p\n", nmemb, size, ret);
    return ret;
}

// ======================================================================
//
//    Implementation
//
// ======================================================================

/* On suppose qu'on ajoute au moins 50 Headers au minimum */
#define MIN_ALLOCATION 50

/* Pour s'aligner sur des frontieres multiples
 * de la taille du type le plus contraignant
 */
#define MOST_RESTRICTING_TYPE long double
typedef union header
{ /* Header de zone */
    struct
    {
        size_t size;       /* Taille de la zone */
        union header *ptr; /* zone libre suivante */
    } info;
    MOST_RESTRICTING_TYPE dummy; /* Ne sert qu'à provoquer un alignement */
} Header;
#define HEADER_SIZE sizeof(Header)

#define NEXT(p) ((p)->info.ptr)
#define SIZE(p) ((p)->info.size)
#define DATA(p) (((Header *) p) + 1)
#define HEADER(d) (((Header *) d) - 1)

/* L'unité de découpage de la mémoire est la taille de Header car ça permet de gérer facilement chaînage
   Le programme appelant malloc reçoit un pointeur et utilise la mémoire comme il veut.
   BLOCKS_TO_ALLOCATE renvoie le nombre de blocs nécessaires pour le malloc demandé, en tenant compte du header */
#define BLOCKS_TO_ALLOCATE(size) (1 + (size + HEADER_SIZE - 1) / HEADER_SIZE)

static Header base   = {{0, &base}}; /* Le pointeur de départ: aucune zone allouée */
static Header *freep = &base;        /* freep pointe sur la 1ère zone libre */

/* ====================================================================== */

/* Fonction utilisée si on n'a pas trouvé assez de place:
 *  - Appelle sbrk pour obtenir plus de mémoire (étendre la zone mémoire adressable par le processus)
 *  - Appelle internal_free pour ajouter la nouvelle zone à la liste des zones libres
 * Commenter le code obfusqué avant de proposer votre solution
 */
static void *allocate_core(size_t size)
{
    if (size < MIN_ALLOCATION)
        size = MIN_ALLOCATION;

    Header *header = mysbrk((intptr_t) (size * HEADER_SIZE));
    if (header == ((Header *) -1))
        return NULL;

    SIZE(header) = size;
    internal_free(DATA(header));
    return freep;
}

/* ====================================================================== */

/* 
 * Fonction interne de malloc:
 *  - Cherche la première zone libre de taille suffisante
 *  - Si pas trouvé, appelle allocate_core pour en obtenir une nouvelle
 */
void *internal_malloc(size_t size)
{
    size            = BLOCKS_TO_ALLOCATE(size);
    Header *current = freep;
    while (SIZE(NEXT(current)) < size)
    {
        Header *next = NEXT(current);
        if (next != freep) // not at end of list
            current = next;
        else
        {
            if (allocate_core(size) == NULL) // will add to the internal list
                return NULL;                 // error
        }
    }
    Header *previous = current;
    current          = NEXT(current);

    assert(SIZE(current) >= size && "invalid current");

    // we know current is valid. But we might need to split it
    if (SIZE(current) > size)
    {
        Header intermediary = {.info = {.size = SIZE(current) - size, .ptr = NEXT(current)}};
        current[size]       = intermediary;
        NEXT(current)       = current + size;
        SIZE(current)       = size;
    }

    // finally, remove the block from the free list
    NEXT(previous) = NEXT(current);

    return DATA(current);
}

bool merge(Header *current, Header *next)
{
    if (current + SIZE(current) - 1 == next) // current and next are contiguous
    {
        SIZE(current) += SIZE(next);
        NEXT(current) = NEXT(next);
        return true;
    }
    return false;
}

/* ====================================================================== */

/* Libère un bloc de mémoire alloué par malloc() ou calloc() 
 * Commenter le code obfusqué avant de proposer votre solution
 */
void internal_free(void *data)
{
    Header *current = HEADER(data);
    // find the block before the one we want to free
    Header *previous = freep;
    while (NEXT(previous) < current)
    {
        // we're back at start, we need to insert this block
        if (NEXT(previous) == freep)
            break;

        previous = NEXT(previous);
    }

    NEXT(current)  = NEXT(previous);
    NEXT(previous) = current;

    if (merge(previous, current))
        current = previous;

    merge(current, NEXT(current));
}

/* ====================================================================== */

void *internal_calloc(size_t nmemb, size_t size)
{
    size_t o_32f887da913db5757a826d698ab32141 = nmemb
                                                * size;
    d_1b841f7f16de41708fbaafa426b7de5d *o_0f90809347ffd00c5c4ec267cae82254;
    o_0f90809347ffd00c5c4ec267cae82254 = d_77f3a163de9b4c9f9ece0fc1e10c8bdd(
        o_32f887da913db5757a826d698ab32141);
    if(o_0f90809347ffd00c5c4ec267cae82254)
        d_15e635a880194479a43052f8e7b25db2(o_0f90809347ffd00c5c4ec267cae82254,
                                           o_32f887da913db5757a826d698ab32141);
    ;
    d_0fca9c5775cb45739917d024a8cecd0c o_0f90809347ffd00c5c4ec267cae82254;
}

void *internal_realloc(void *ptr, size_t size)
{
    Header *o_e5366b2aa25f94f0bc5ac1dbd49babdb, *o_648f12acaa0629f9a10db494d4289b3b;
    Header *o_3ba6675c20df730e5950c075ef7156e1 = ((Header *) ptr)
                                                 - (0x0000000000000002 + 0x0000000000000201
                                                    + 0x0000000000000801 - 0x0000000000000A03);
    if(!ptr)
        d_0fca9c5775cb45739917d024a8cecd0c d_77f3a163de9b4c9f9ece0fc1e10c8bdd(
            size);
    ;
    if(!size)
    {
        d_ce25df4bb9ec4b338f519302e17e923b(ptr);
        d_0fca9c5775cb45739917d024a8cecd0c d_cd7b23f6cf614dac81521ef674a2773f;
    };
    d_de577e1e25b74b728d36e9df44fbee8c o_1d57cc1cfdf9de8719f300f48e1cd4fb = d_6832373b099d4a0a8635f83b67263ac2(
        size);
    if((o_1d57cc1cfdf9de8719f300f48e1cd4fb
         > d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1))
        & !!(o_1d57cc1cfdf9de8719f300f48e1cd4fb
             > d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1)))
    {
        d_3e01c509b63b4fb4bb2cd5580d8e177b(
            o_648f12acaa0629f9a10db494d4289b3b = d_f0e6fce3d2144597bc3670844da06bdb,
            o_e5366b2aa25f94f0bc5ac1dbd49babdb = d_d8aeefb7d2a54e30a43082cf2a535006(
                d_f0e6fce3d2144597bc3670844da06bdb);
            o_648f12acaa0629f9a10db494d4289b3b = o_e5366b2aa25f94f0bc5ac1dbd49babdb,
            o_e5366b2aa25f94f0bc5ac1dbd49babdb = d_d8aeefb7d2a54e30a43082cf2a535006(
                o_e5366b2aa25f94f0bc5ac1dbd49babdb);)
        {
            if(d_d8aeefb7d2a54e30a43082cf2a535006(o_648f12acaa0629f9a10db494d4289b3b)
                    == d_f0e6fce3d2144597bc3670844da06bdb
                || (o_e5366b2aa25f94f0bc5ac1dbd49babdb > o_3ba6675c20df730e5950c075ef7156e1)
                       & !!(o_e5366b2aa25f94f0bc5ac1dbd49babdb > o_3ba6675c20df730e5950c075ef7156e1))
            {
                d_4e1c50ae9c1e464c8795360721ed2fd7;
            };
        };
        if(o_3ba6675c20df730e5950c075ef7156e1
                + d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1)
            == o_e5366b2aa25f94f0bc5ac1dbd49babdb)
        {
            if((d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1)
                     + d_c504595b54044fc1b9c8c74e8f0b706b(o_e5366b2aa25f94f0bc5ac1dbd49babdb)
                 >= o_1d57cc1cfdf9de8719f300f48e1cd4fb)
                & !!(d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1)
                         + d_c504595b54044fc1b9c8c74e8f0b706b(o_e5366b2aa25f94f0bc5ac1dbd49babdb)
                     >= o_1d57cc1cfdf9de8719f300f48e1cd4fb))
            {
                if((d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1)
                     + d_c504595b54044fc1b9c8c74e8f0b706b(o_e5366b2aa25f94f0bc5ac1dbd49babdb))
                    == o_1d57cc1cfdf9de8719f300f48e1cd4fb)
                {
                    d_d8aeefb7d2a54e30a43082cf2a535006(o_648f12acaa0629f9a10db494d4289b3b)
                        = d_d8aeefb7d2a54e30a43082cf2a535006(o_e5366b2aa25f94f0bc5ac1dbd49babdb);
                }
                d_ba16cdac161549a588a46912ca7874cb
                {
                    d_de577e1e25b74b728d36e9df44fbee8c o_19b04276b5c7f1670b9e69733f73ac27
                        = o_1d57cc1cfdf9de8719f300f48e1cd4fb
                          - d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1);
                    Header *o_716540822b981cf8245aa482e7fdc154 = o_e5366b2aa25f94f0bc5ac1dbd49babdb
                                                                 + o_19b04276b5c7f1670b9e69733f73ac27;
                    d_c504595b54044fc1b9c8c74e8f0b706b(o_716540822b981cf8245aa482e7fdc154)
                        = d_c504595b54044fc1b9c8c74e8f0b706b(o_e5366b2aa25f94f0bc5ac1dbd49babdb)
                          - o_19b04276b5c7f1670b9e69733f73ac27;
                    d_d8aeefb7d2a54e30a43082cf2a535006(o_716540822b981cf8245aa482e7fdc154)
                        = d_d8aeefb7d2a54e30a43082cf2a535006(o_e5366b2aa25f94f0bc5ac1dbd49babdb);
                    d_d8aeefb7d2a54e30a43082cf2a535006(o_648f12acaa0629f9a10db494d4289b3b)
                        = o_716540822b981cf8245aa482e7fdc154;
                    d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1)
                        = o_1d57cc1cfdf9de8719f300f48e1cd4fb;
                };
                d_0fca9c5775cb45739917d024a8cecd0c ptr;
            };
        };
        d_1b841f7f16de41708fbaafa426b7de5d *o_95045283e1bb0472f3b88341825e086e
            = d_77f3a163de9b4c9f9ece0fc1e10c8bdd(size);
        if(o_95045283e1bb0472f3b88341825e086e)
        {
            d_a9b416a707ab46538701c47f90c63553(o_95045283e1bb0472f3b88341825e086e,
                                               ptr,
                                               (d_c504595b54044fc1b9c8c74e8f0b706b(
                                                    o_3ba6675c20df730e5950c075ef7156e1)
                                                - (0x0000000000000002 + 0x0000000000000201
                                                   + 0x0000000000000801 - 0x0000000000000A03))
                                                   * d_b6e8837c0ef040cfa871d5dcb9dc1f61(Header));
            d_ce25df4bb9ec4b338f519302e17e923b(ptr);
        };
        d_0fca9c5775cb45739917d024a8cecd0c o_95045283e1bb0472f3b88341825e086e;
    };
    d_0fca9c5775cb45739917d024a8cecd0c ptr;
}