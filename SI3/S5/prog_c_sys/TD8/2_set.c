#include <memory.h>
#include <stdio.h>

#define CHAR_SIZE 8    /* nombre de bits dans un char */
#define MAX_BIGSET 125 /* nombre de cellules dans un ensemble
*/
#define MAX_VAL (CHAR_SIZE * MAX_BIGSET)
typedef unsigned char BigSet[MAX_BIGSET]; /* un ensemble dans [0 .. MAX_VAL[ */

/* initialiser s à l'ensemble vide */
void BigSet_init(BigSet s) {
    memset(s, 0, MAX_BIGSET);
}

void get_idx(int num, int *cell, int *bit) {
    *cell = num / CHAR_SIZE;
    *bit = num % CHAR_SIZE;
}

/* ajouter i dans s */
void BigSet_add(BigSet s, int i) {
    int cell, bit;
    get_idx(i, &cell, &bit);
    s[cell] |= (1 << bit);
}
/* 0 si i ∉ s et ≠ 0 sinon */
int BigSet_is_in(const BigSet s, int i) {
    int cell, bit;
    get_idx(i, &cell, &bit);
    return s[cell] & (1 << bit);
}
/* afficher les éléments de s */
void BigSet_print(BigSet s) {
    printf("{");
    for(int i = 0; i < MAX_VAL; ++i)
    {
        if(BigSet_is_in(s, i))
            printf("%d, ", i);
    }
    printf("}");
}
/* range dans res le résultat de l'intersection de s1 et s2 */
void BigSet_inter(const BigSet s1, const BigSet s2, BigSet res) {
    for(int i = 0; i < MAX_BIGSET; ++i)
    {
        res[i] = s1[i] & s2[i];
    }
}

int main() {
    BigSet s1, s2, res;
    BigSet_init(s1);
    BigSet_init(s2);
    BigSet_init(res);

    BigSet_add(s1, 0);
    BigSet_add(s1, 75);
    BigSet_add(s1, 124);

    BigSet_add(s2, 0);
    BigSet_add(s2, 75);
    BigSet_add(s2, 125);

    BigSet_inter(s1, s2, res);

    BigSet_print(s1);
printf(" ∩ ");
    BigSet_print(s2);
printf(" = ");
    BigSet_print(res);
    return 0;
}