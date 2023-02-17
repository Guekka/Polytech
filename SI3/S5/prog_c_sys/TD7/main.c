/*                                              -*- coding: utf-8 -*-
 *
 * Utilisation de la table
 *
 * Ce code est complétement indépendant de l'implémentation de la table
 *
 *           Author: Erick Gallesio [eg@unice.fr]
 *    Creation date:  5-Dec-2013 17:29 (eg)
 * Last file update:  5-Dec-2016 15:56 (eg)
 */
#include "table.h"

#include <assert.h>
#include <ctype.h>
#include <stdio.h>
#include <string.h>

#define MAXIDENT 255 /* taille max d'un identificateur */

#define isletter(c) (isalpha(c) || (c) == '_')
#define isletter_or_digit(c) (isletter(c) || isdigit(c))

// Fonction retournant un identificateur lu sur stdin
// Renvoie 0 si EOF rencontré
// On suppose ici qu'un identificateur est simplement une suite de lettres et
// que sa
//   taille est limitée a MAXIDENT-1 caractères (la fin est perdue sinon)

char *lire_mot(void)
{
    int c;
    static char mot[MAXIDENT + 1]; /* Donnee statique car renvoyée par la fonction */
    char *pt;

    pt = mot;
    while ((c = getchar()) != EOF)
    {
        if (isletter(c))
        {
            do
            {
                if (pt < &mot[MAXIDENT])
                    *pt++ = c;
                c = getchar();
            } while (isletter_or_digit(c));
            break;
        }
    }

    if (pt == mot)
    { //  On est sorti en EOF
        return NULL;
    }
    else
    { // On a lu un mot
        *pt = '\0';
        return mot;
    }
}

// Affichage des mots qui on un nombre pair de lettres
void affiche_pair(char *s, int n)
{
    int len = strlen(s);

    if ((len & 1) == 0) /* Bit de poids faible == 0 => longeur paire */
        printf("pair %s (len = %d)\n", s, len);
}

// Affichage des mots qui appraissent plus de 3 fois
void affiche_plus_que_trois(char *s, int nb)
{
    if (nb > 3)
        printf("%s (%d fois)\n", s, nb);
}

// ======================================================================
int main(void)
{
    Table table;
    char *mot;

    table = creer_table();

    while ((mot = lire_mot()))
        ajouter_table(&table, mot);

    /* Impression de la table triee */
    imprimer_table(table);

    /* Affichage de la table triee avant les idents ayant un nombre pair de
   * lettres */
    printf("----------\n");
    appliquer_table(table, affiche_pair);

    /* Affichage dee mots qui apparaissent plus de 3 fois */
    printf("----------\n");
    appliquer_table(table, affiche_plus_que_trois);

    /* Destruction de la table */
    detruire_table(&table);
    return 0;
}