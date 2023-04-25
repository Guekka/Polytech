/*
 * my-malloc.c  -- Implementation de malloc, free, calloc, realloc
 *
 * Implémentation first-fit pour malloc
 *
 * Erick Gallesio (2020/04/10)
 * Stéphane Lavirotte (2023/04/13)
 */

#include "mymalloc.h"

#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include "obfuscate.h"

// ======================================================================
//
//    Définition des fonctions. NE PAS MODIFIER CETTE PREMIERE PARTIE.
//
// ======================================================================

// Les fonctions internes à implémenter
void *internal_malloc(size_t size);
void internal_free(void *ptr);
void *internal_calloc(size_t nmemb, size_t size);
void *internal_realloc(void *ptr, size_t size);

// Les fonctions externes: ces fonctions appellent les fonctions internes
//  que vous avez à écrire en y ajoutant une trace qui est utilisée par
//  to-html et villoc.py pour générer les .html
void *mysbrk(intptr_t increment) {
    void *ret, *old, *tmp;
    static int first_call = 1;

    if (first_call) {
        old = sbrk(0);
        fprintf(stderr, "SYS_brk(0)                    =  %p\n", old);
        first_call = 0;
    }

    ret = sbrk(increment);
    tmp = ret + increment;
    fprintf(stderr, "SYS_brk(%p)                    = %p\n", tmp, tmp);
    return ret;
}

void *mymalloc(size_t size) {
    void *ret = internal_malloc(size);
    fprintf(stderr, "mymalloc(%ld)                     = %p\n", size, ret);
    return ret;
}

void myfree(void *ptr) {
    internal_free(ptr);
    fprintf(stderr, "myfree(%p)                    = <void>\n", ptr);
}

void *myrealloc(void *ptr, size_t size) {
    void *ret = internal_realloc(ptr, size);
    fprintf(stderr, "myrealloc(%p, %ld)                     = %p\n", ptr, size, ret);
    return ret;
}

void *mycalloc(size_t nmemb, size_t size) {
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
typedef union header { /* Header de zone */
    struct {
        size_t size;       /* Taille du zone */
        union header *ptr; /* zone libre suivante */
    } info;
    MOST_RESTRICTING_TYPE dummy; /* Ne sert qu'a provoquer un alignement */
} Header;
#define HEADER_SIZE sizeof(Header)

#define NEXT(p) ((p)->info.ptr)
#define SIZE(p) ((p)->info.size)

/* L'unité de découpage de la mémoire est la taille de Header car ça permet de gérer facilement chaînage
   Le programme appelant malloc reçoit un pointeur et utilise la mémoire comme il veut.
   BLOCKS_TO_ALLOCATE renvoie le nombre de blocs nécessaires pour le malloc demandé, en tenant compte du header */
#define BLOCKS_TO_ALLOCATE(size) (1 + (size + HEADER_SIZE - 1) / HEADER_SIZE)

static Header base = {{0, &base}}; /* Le pointeur de départ: aucune zone allouée */
static Header *freep = &base;      /* freep pointe sur la 1ère zone libre */

/* ====================================================================== */

/* Fonction utilisée si on n'a pas trouvé assez de place:
 *  - Appelle sbrk pour obtenir plus de mémoire (étendre la zone mémoire adressable par le processus)
 *  - Appelle internal_free pour ajouter la nouvelle zone à la liste des zones libres
 * Commenter le code obfusqué avant de proposer votre solution
 */
static void *allocate_core(size_t o_ff0da02c02d81e161f53f59fbf94f724) {
    d_25f02d83e74b494398384a979fc5905e *o_e885ad26a275a307e21f453748b2f016;
    d_9704fa3dc09c42a7a0bf66eead5b07c8((o_ff0da02c02d81e161f53f59fbf94f724 < d_97961cbeceac434c8b591a95007fa3eb) & !!(o_ff0da02c02d81e161f53f59fbf94f724 < d_97961cbeceac434c8b591a95007fa3eb))
        o_ff0da02c02d81e161f53f59fbf94f724 = d_97961cbeceac434c8b591a95007fa3eb;
    ;
    o_e885ad26a275a307e21f453748b2f016 = d_afaf5fd059d94080817053099306ac75(o_ff0da02c02d81e161f53f59fbf94f724 * d_b6e8837c0ef040cfa871d5dcb9dc1f61(d_25f02d83e74b494398384a979fc5905e));
    d_9704fa3dc09c42a7a0bf66eead5b07c8(o_e885ad26a275a307e21f453748b2f016 == ((d_1b841f7f16de41708fbaafa426b7de5d *)-(0x0000000000000002 + 0x0000000000000201 + 0x0000000000000801 - 0x0000000000000A03)))
        d_0fca9c5775cb45739917d024a8cecd0c d_cd7b23f6cf614dac81521ef674a2773f;
    ;
    d_c504595b54044fc1b9c8c74e8f0b706b(o_e885ad26a275a307e21f453748b2f016) = o_ff0da02c02d81e161f53f59fbf94f724;
    d_ce25df4bb9ec4b338f519302e17e923b(o_e885ad26a275a307e21f453748b2f016 + (0x0000000000000002 + 0x0000000000000201 + 0x0000000000000801 - 0x0000000000000A03));
    d_0fca9c5775cb45739917d024a8cecd0c d_f0e6fce3d2144597bc3670844da06bdb;
}

/* ====================================================================== */

/* 
 * Fonction interne de malloc:
 *  - Cherche la première zone libre de taille suffisante
 *  - Si pas trouvé, appelle allocate_core pour en obtenir une nouvelle
 * Commenter le code obfusqué avant de proposer votre solution
 */
void *internal_malloc(size_t o_624baec7fe25b780476c902e5a225945) {
    d_25f02d83e74b494398384a979fc5905e *o_5a1ca1e28af1dee42c510b5ab5b7699f, *o_80001f3007bad536645a8f26f2f14ef0;
    d_de577e1e25b74b728d36e9df44fbee8c o_a14ecdac4a90374110b8b29ff04ee1a7;
    o_a14ecdac4a90374110b8b29ff04ee1a7 = d_6832373b099d4a0a8635f83b67263ac2(o_624baec7fe25b780476c902e5a225945);
    d_3e01c509b63b4fb4bb2cd5580d8e177b(o_80001f3007bad536645a8f26f2f14ef0 = d_f0e6fce3d2144597bc3670844da06bdb, o_5a1ca1e28af1dee42c510b5ab5b7699f = d_d8aeefb7d2a54e30a43082cf2a535006(d_f0e6fce3d2144597bc3670844da06bdb); o_80001f3007bad536645a8f26f2f14ef0 = o_5a1ca1e28af1dee42c510b5ab5b7699f, o_5a1ca1e28af1dee42c510b5ab5b7699f = d_d8aeefb7d2a54e30a43082cf2a535006(o_5a1ca1e28af1dee42c510b5ab5b7699f);) {
        d_9704fa3dc09c42a7a0bf66eead5b07c8((d_c504595b54044fc1b9c8c74e8f0b706b(o_5a1ca1e28af1dee42c510b5ab5b7699f) >= o_a14ecdac4a90374110b8b29ff04ee1a7) & !!(d_c504595b54044fc1b9c8c74e8f0b706b(o_5a1ca1e28af1dee42c510b5ab5b7699f) >= o_a14ecdac4a90374110b8b29ff04ee1a7)) {
            d_9704fa3dc09c42a7a0bf66eead5b07c8(d_c504595b54044fc1b9c8c74e8f0b706b(o_5a1ca1e28af1dee42c510b5ab5b7699f) == o_a14ecdac4a90374110b8b29ff04ee1a7) {
                d_d8aeefb7d2a54e30a43082cf2a535006(o_80001f3007bad536645a8f26f2f14ef0) = d_d8aeefb7d2a54e30a43082cf2a535006(o_5a1ca1e28af1dee42c510b5ab5b7699f);
            }
            d_ba16cdac161549a588a46912ca7874cb {
                d_25f02d83e74b494398384a979fc5905e *o_9d80e51e957d1e3075a68872855e1902 = o_5a1ca1e28af1dee42c510b5ab5b7699f + o_a14ecdac4a90374110b8b29ff04ee1a7;
                d_c504595b54044fc1b9c8c74e8f0b706b(o_9d80e51e957d1e3075a68872855e1902) = d_c504595b54044fc1b9c8c74e8f0b706b(o_5a1ca1e28af1dee42c510b5ab5b7699f) - o_a14ecdac4a90374110b8b29ff04ee1a7;
                d_d8aeefb7d2a54e30a43082cf2a535006(o_9d80e51e957d1e3075a68872855e1902) = d_d8aeefb7d2a54e30a43082cf2a535006(o_5a1ca1e28af1dee42c510b5ab5b7699f);
                d_d8aeefb7d2a54e30a43082cf2a535006(o_80001f3007bad536645a8f26f2f14ef0) = o_9d80e51e957d1e3075a68872855e1902;
                d_c504595b54044fc1b9c8c74e8f0b706b(o_5a1ca1e28af1dee42c510b5ab5b7699f) = o_a14ecdac4a90374110b8b29ff04ee1a7;
            };
            d_0fca9c5775cb45739917d024a8cecd0c(d_1b841f7f16de41708fbaafa426b7de5d *)(o_5a1ca1e28af1dee42c510b5ab5b7699f + (0x0000000000000002 + 0x0000000000000201 + 0x0000000000000801 - 0x0000000000000A03));
        };
        d_9704fa3dc09c42a7a0bf66eead5b07c8(o_5a1ca1e28af1dee42c510b5ab5b7699f == d_f0e6fce3d2144597bc3670844da06bdb) {
            d_9704fa3dc09c42a7a0bf66eead5b07c8((o_5a1ca1e28af1dee42c510b5ab5b7699f = d_2a14cc81eb4144968d5d6cd2113a3ca8(o_a14ecdac4a90374110b8b29ff04ee1a7)) == d_cd7b23f6cf614dac81521ef674a2773f)
                d_0fca9c5775cb45739917d024a8cecd0c d_cd7b23f6cf614dac81521ef674a2773f;
            ;
        };
    };
    d_0fca9c5775cb45739917d024a8cecd0c d_cd7b23f6cf614dac81521ef674a2773f;
}

/* ====================================================================== */

/* Libère un bloc de mémoire alloué par malloc() ou calloc() 
 * Commenter le code obfusqué avant de proposer votre solution
 */
void internal_free(void *o_279d7785802f49a15b5f695ed8f19d8b) {
    d_25f02d83e74b494398384a979fc5905e *o_e15d2c808b08eba355ee7e110fccd69d, *o_2c9a385d2dd75367e19df9aab65c0dd7, *o_4c6613b3e3aa91a19b2335d21ae5c09d;
    d_9704fa3dc09c42a7a0bf66eead5b07c8(!o_279d7785802f49a15b5f695ed8f19d8b)
        d_0fca9c5775cb45739917d024a8cecd0c;
    ;
    o_e15d2c808b08eba355ee7e110fccd69d = ((d_25f02d83e74b494398384a979fc5905e *)o_279d7785802f49a15b5f695ed8f19d8b) - (0x0000000000000002 + 0x0000000000000201 + 0x0000000000000801 - 0x0000000000000A03);
    d_3e01c509b63b4fb4bb2cd5580d8e177b(o_4c6613b3e3aa91a19b2335d21ae5c09d = d_f0e6fce3d2144597bc3670844da06bdb, o_2c9a385d2dd75367e19df9aab65c0dd7 = d_d8aeefb7d2a54e30a43082cf2a535006(d_f0e6fce3d2144597bc3670844da06bdb); o_4c6613b3e3aa91a19b2335d21ae5c09d = o_2c9a385d2dd75367e19df9aab65c0dd7, o_2c9a385d2dd75367e19df9aab65c0dd7 = d_d8aeefb7d2a54e30a43082cf2a535006(o_2c9a385d2dd75367e19df9aab65c0dd7);) {
        d_9704fa3dc09c42a7a0bf66eead5b07c8(d_d8aeefb7d2a54e30a43082cf2a535006(o_4c6613b3e3aa91a19b2335d21ae5c09d) == d_f0e6fce3d2144597bc3670844da06bdb || (o_2c9a385d2dd75367e19df9aab65c0dd7 > o_e15d2c808b08eba355ee7e110fccd69d) & !!(o_2c9a385d2dd75367e19df9aab65c0dd7 > o_e15d2c808b08eba355ee7e110fccd69d)) {
            d_4e1c50ae9c1e464c8795360721ed2fd7;
        };
    };
    d_9704fa3dc09c42a7a0bf66eead5b07c8(o_e15d2c808b08eba355ee7e110fccd69d + d_c504595b54044fc1b9c8c74e8f0b706b(o_e15d2c808b08eba355ee7e110fccd69d) == o_2c9a385d2dd75367e19df9aab65c0dd7) {
        d_c504595b54044fc1b9c8c74e8f0b706b(o_e15d2c808b08eba355ee7e110fccd69d) += d_c504595b54044fc1b9c8c74e8f0b706b(o_2c9a385d2dd75367e19df9aab65c0dd7);
        d_d8aeefb7d2a54e30a43082cf2a535006(o_e15d2c808b08eba355ee7e110fccd69d) = d_d8aeefb7d2a54e30a43082cf2a535006(o_2c9a385d2dd75367e19df9aab65c0dd7);
    }
    d_ba16cdac161549a588a46912ca7874cb {
        d_d8aeefb7d2a54e30a43082cf2a535006(o_e15d2c808b08eba355ee7e110fccd69d) = o_2c9a385d2dd75367e19df9aab65c0dd7;
    };
    d_9704fa3dc09c42a7a0bf66eead5b07c8(o_4c6613b3e3aa91a19b2335d21ae5c09d + d_c504595b54044fc1b9c8c74e8f0b706b(o_4c6613b3e3aa91a19b2335d21ae5c09d) == o_e15d2c808b08eba355ee7e110fccd69d) {
        d_c504595b54044fc1b9c8c74e8f0b706b(o_4c6613b3e3aa91a19b2335d21ae5c09d) += d_c504595b54044fc1b9c8c74e8f0b706b(o_e15d2c808b08eba355ee7e110fccd69d);
        d_d8aeefb7d2a54e30a43082cf2a535006(o_4c6613b3e3aa91a19b2335d21ae5c09d) = d_d8aeefb7d2a54e30a43082cf2a535006(o_e15d2c808b08eba355ee7e110fccd69d);
    }
    d_ba16cdac161549a588a46912ca7874cb {
        d_d8aeefb7d2a54e30a43082cf2a535006(o_4c6613b3e3aa91a19b2335d21ae5c09d) = o_e15d2c808b08eba355ee7e110fccd69d;
    };
}

/* ====================================================================== */

void *internal_calloc(size_t o_dacd7d9656b665abd56e43953773a249, size_t o_d88f1339ff278c9eec145bab6aa8e619) {
    size_t o_32f887da913db5757a826d698ab32141 = o_dacd7d9656b665abd56e43953773a249 * o_d88f1339ff278c9eec145bab6aa8e619;
    d_1b841f7f16de41708fbaafa426b7de5d *o_0f90809347ffd00c5c4ec267cae82254;
    o_0f90809347ffd00c5c4ec267cae82254 = d_77f3a163de9b4c9f9ece0fc1e10c8bdd(o_32f887da913db5757a826d698ab32141);
    d_9704fa3dc09c42a7a0bf66eead5b07c8(o_0f90809347ffd00c5c4ec267cae82254)
        d_15e635a880194479a43052f8e7b25db2(o_0f90809347ffd00c5c4ec267cae82254, o_32f887da913db5757a826d698ab32141);
    ;
    d_0fca9c5775cb45739917d024a8cecd0c o_0f90809347ffd00c5c4ec267cae82254;
}

void *internal_realloc(void *o_56fe1a4e066373cd1b73b9b9715d0a69, size_t o_026d4848dc9cd401db1d204c1a67bb5b) {
    d_25f02d83e74b494398384a979fc5905e *o_e5366b2aa25f94f0bc5ac1dbd49babdb, *o_648f12acaa0629f9a10db494d4289b3b;
    d_25f02d83e74b494398384a979fc5905e *o_3ba6675c20df730e5950c075ef7156e1 = ((d_25f02d83e74b494398384a979fc5905e *)o_56fe1a4e066373cd1b73b9b9715d0a69) - (0x0000000000000002 + 0x0000000000000201 + 0x0000000000000801 - 0x0000000000000A03);
    d_9704fa3dc09c42a7a0bf66eead5b07c8(!o_56fe1a4e066373cd1b73b9b9715d0a69)
        d_0fca9c5775cb45739917d024a8cecd0c d_77f3a163de9b4c9f9ece0fc1e10c8bdd(o_026d4848dc9cd401db1d204c1a67bb5b);
    ;
    d_9704fa3dc09c42a7a0bf66eead5b07c8(!o_026d4848dc9cd401db1d204c1a67bb5b) {
        d_ce25df4bb9ec4b338f519302e17e923b(o_56fe1a4e066373cd1b73b9b9715d0a69);
        d_0fca9c5775cb45739917d024a8cecd0c d_cd7b23f6cf614dac81521ef674a2773f;
    };
    d_de577e1e25b74b728d36e9df44fbee8c o_1d57cc1cfdf9de8719f300f48e1cd4fb = d_6832373b099d4a0a8635f83b67263ac2(o_026d4848dc9cd401db1d204c1a67bb5b);
    d_9704fa3dc09c42a7a0bf66eead5b07c8((o_1d57cc1cfdf9de8719f300f48e1cd4fb > d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1)) & !!(o_1d57cc1cfdf9de8719f300f48e1cd4fb > d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1))) {
        d_3e01c509b63b4fb4bb2cd5580d8e177b(o_648f12acaa0629f9a10db494d4289b3b = d_f0e6fce3d2144597bc3670844da06bdb, o_e5366b2aa25f94f0bc5ac1dbd49babdb = d_d8aeefb7d2a54e30a43082cf2a535006(d_f0e6fce3d2144597bc3670844da06bdb); o_648f12acaa0629f9a10db494d4289b3b = o_e5366b2aa25f94f0bc5ac1dbd49babdb, o_e5366b2aa25f94f0bc5ac1dbd49babdb = d_d8aeefb7d2a54e30a43082cf2a535006(o_e5366b2aa25f94f0bc5ac1dbd49babdb);) {
            d_9704fa3dc09c42a7a0bf66eead5b07c8(d_d8aeefb7d2a54e30a43082cf2a535006(o_648f12acaa0629f9a10db494d4289b3b) == d_f0e6fce3d2144597bc3670844da06bdb || (o_e5366b2aa25f94f0bc5ac1dbd49babdb > o_3ba6675c20df730e5950c075ef7156e1) & !!(o_e5366b2aa25f94f0bc5ac1dbd49babdb > o_3ba6675c20df730e5950c075ef7156e1)) {
                d_4e1c50ae9c1e464c8795360721ed2fd7;
            };
        };
        d_9704fa3dc09c42a7a0bf66eead5b07c8(o_3ba6675c20df730e5950c075ef7156e1 + d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1) == o_e5366b2aa25f94f0bc5ac1dbd49babdb) {
            d_9704fa3dc09c42a7a0bf66eead5b07c8((d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1) + d_c504595b54044fc1b9c8c74e8f0b706b(o_e5366b2aa25f94f0bc5ac1dbd49babdb) >= o_1d57cc1cfdf9de8719f300f48e1cd4fb) & !!(d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1) + d_c504595b54044fc1b9c8c74e8f0b706b(o_e5366b2aa25f94f0bc5ac1dbd49babdb) >= o_1d57cc1cfdf9de8719f300f48e1cd4fb)) {
                d_9704fa3dc09c42a7a0bf66eead5b07c8((d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1) + d_c504595b54044fc1b9c8c74e8f0b706b(o_e5366b2aa25f94f0bc5ac1dbd49babdb)) == o_1d57cc1cfdf9de8719f300f48e1cd4fb) {
                    d_d8aeefb7d2a54e30a43082cf2a535006(o_648f12acaa0629f9a10db494d4289b3b) = d_d8aeefb7d2a54e30a43082cf2a535006(o_e5366b2aa25f94f0bc5ac1dbd49babdb);
                }
                d_ba16cdac161549a588a46912ca7874cb {
                    d_de577e1e25b74b728d36e9df44fbee8c o_19b04276b5c7f1670b9e69733f73ac27 = o_1d57cc1cfdf9de8719f300f48e1cd4fb - d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1);
                    d_25f02d83e74b494398384a979fc5905e *o_716540822b981cf8245aa482e7fdc154 = o_e5366b2aa25f94f0bc5ac1dbd49babdb + o_19b04276b5c7f1670b9e69733f73ac27;
                    d_c504595b54044fc1b9c8c74e8f0b706b(o_716540822b981cf8245aa482e7fdc154) = d_c504595b54044fc1b9c8c74e8f0b706b(o_e5366b2aa25f94f0bc5ac1dbd49babdb) - o_19b04276b5c7f1670b9e69733f73ac27;
                    d_d8aeefb7d2a54e30a43082cf2a535006(o_716540822b981cf8245aa482e7fdc154) = d_d8aeefb7d2a54e30a43082cf2a535006(o_e5366b2aa25f94f0bc5ac1dbd49babdb);
                    d_d8aeefb7d2a54e30a43082cf2a535006(o_648f12acaa0629f9a10db494d4289b3b) = o_716540822b981cf8245aa482e7fdc154;
                    d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1) = o_1d57cc1cfdf9de8719f300f48e1cd4fb;
                };
                d_0fca9c5775cb45739917d024a8cecd0c o_56fe1a4e066373cd1b73b9b9715d0a69;
            };
        };
        d_1b841f7f16de41708fbaafa426b7de5d *o_95045283e1bb0472f3b88341825e086e = d_77f3a163de9b4c9f9ece0fc1e10c8bdd(o_026d4848dc9cd401db1d204c1a67bb5b);
        d_9704fa3dc09c42a7a0bf66eead5b07c8(o_95045283e1bb0472f3b88341825e086e) {
            d_a9b416a707ab46538701c47f90c63553(o_95045283e1bb0472f3b88341825e086e, o_56fe1a4e066373cd1b73b9b9715d0a69, (d_c504595b54044fc1b9c8c74e8f0b706b(o_3ba6675c20df730e5950c075ef7156e1) - (0x0000000000000002 + 0x0000000000000201 + 0x0000000000000801 - 0x0000000000000A03)) * d_b6e8837c0ef040cfa871d5dcb9dc1f61(d_25f02d83e74b494398384a979fc5905e));
            d_ce25df4bb9ec4b338f519302e17e923b(o_56fe1a4e066373cd1b73b9b9715d0a69);
        };
        d_0fca9c5775cb45739917d024a8cecd0c o_95045283e1bb0472f3b88341825e086e;
    };
    d_0fca9c5775cb45739917d024a8cecd0c o_56fe1a4e066373cd1b73b9b9715d0a69;
}