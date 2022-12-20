#include "table.h"

#include <assert.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

typedef struct table_el
{
    char *str;
    int compteur;
} table_el;

#define TABLE_TYPE table_el

typedef struct table table;

size_t table_required_bytes(table *vec);

void table_grow(table *vec);

void table_push_back(table *vec, TABLE_TYPE element);

struct table
{
    TABLE_TYPE *data;
    size_t len;
    size_t capacity;
};

size_t table_required_bytes(table *vec)
{
    return vec->capacity * sizeof(TABLE_TYPE);
}

void table_grow(table *vec)
{
    vec->capacity *= 2;
    vec->data = realloc(vec->data, table_required_bytes(vec));
    assert(vec->data != NULL);
}

void table_push_back(table *vec, TABLE_TYPE element)
{
    if (vec->len == vec->capacity)
        table_grow(vec);

    vec->data[vec->len++] = element;
}

void table_insert(table *vec, size_t pos, TABLE_TYPE element)
{
    if (vec->len == vec->capacity)
        table_grow(vec);

    memmove(vec->data + pos + 1, vec->data + pos, vec->len - pos - 1);
    vec->data[pos] = element;
    ++vec->len;
}

void table_pop_back(table *vec)
{
    --vec->len;
}

TABLE_TYPE *table_back(table *vec)
{
    return &vec->data[vec->len - 1];
}

//
// Fonctions elementaires de manipulation de la table
//

/* Creation d'une table vide */
Table creer_table(void)
{
    const size_t default_capacity = 500;

    Table vec     = malloc(sizeof(table));
    vec->capacity = default_capacity;
    vec->len      = 0;
    vec->data     = NULL;

    table_grow(vec);

    return vec;
}

/* Insertion d'un élément dans la t triée. Si l'élément est déjà
 * présent dans la t, le compteur d'occurrences est incrémenté.
 * La fonction renvoie le nombre actuel d'occurrences de elt */
int ajouter_table(Table *t, char *elt)
{
    for (size_t i = 0; i < (*t)->len; ++i)
    {
        int cmp_res = strcmp(elt, (*t)->data[i].str);
        if (cmp_res == 0)
        {
            return ++(*t)->data[i].compteur;
        }
        if (cmp_res < 0)
        {
            TABLE_TYPE new_el = {.str = elt, .compteur = 1};
            table_insert(*t, i, new_el);
            return 1;
        }
    }

    TABLE_TYPE new_el = {.str = elt, .compteur = 1};
    table_push_back(*t, new_el);
    return 1;
}

/* Impression triée des éléments de la table */
void imprimer_table(Table table)
{
    for (size_t i = 0; i < table->len; ++i)
        printf("%s: %i\n", table->data[i].str, table->data[i].compteur);
}

/* Appel d'une fonction sur chacun des éléments de la table */
void appliquer_table(Table table, t_fonction fonction)
{
    for (size_t i = 0; i < table->len; ++i)
        fonction(&table->data[i].str, &table->data[i].compteur);
}

/* Recherche du nombre d'occurrences d'un élément */
int rechercher_table(Table table, char *elt)
{
    for (int i = 0; i < table->len; ++i)
        if (strcmp(table->data[i].str, elt) == 0)
            return i;
    return -1;
}

/* Destruction d'une table */
void detruire_table(Table *table)
{
    free((*table)->data);
    free(table);
}
