#include "table.h"

#include <malloc.h>
#include <string.h>

//
// Fonctions elementaires de manipulation de la table
//

typedef struct table
{
    char *str;
    int counter;
    Table next;
} table;

/* Creation d'une table vide */
Table creer_table(void)
{
    Table t    = (Table) malloc(sizeof(table));
    t->counter = 0;
    t->str     = NULL;
    t->next    = NULL;
}

void reset_node(Table *table, char *elt)
{
    free((*table)->str);

    (*table)->str = malloc(sizeof(char) * strlen(elt));
    strcpy((*table)->str, elt);

    (*table)->counter = 1;
}

/* Insertion d'un élément dans la table triée. Si l'élément est déjà
 * présent dans la table, le compteur d'occurrences est incrémenté.
 * La fonction renvoie le nombre actuel d'occurrences de elt */
int ajouter_table(Table *table, char *elt)
{
    if ((*table)->str == NULL)
        reset_node(table, elt);

    int cmp_res = strcmp(elt, (*table)->str);
    if (cmp_res == 0)
    {
        return ++(*table)->counter;
    }
    if (cmp_res > 0)
    {
        Table new = creer_table();
        reset_node(&new, elt);
    }

    if ((*table)->next == NULL) {}
}

/* Impression triée des éléments de la table */
void imprimer_table(Table table) {}

/* Appel d'une fonction sur chacun des éléments de la table */
void appliquer_table(Table table, t_fonction fonction) {}

/* Recherche du nombre d'occurrences d'un élément */
int rechercher_table(Table table, char *elt) {}

/* Destruction d'une table */
void detruire_table(Table *table) {}
