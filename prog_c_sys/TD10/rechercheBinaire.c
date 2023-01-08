#include<stdio.h>
#include<stdlib.h>


int rechercheBinaire(const int tab[], int x, int i, int j) {
    int gau = i;
    int droite = j;
    while (gau <= droite) {
        int milieu = (gau + droite) / 2;
        if (tab[milieu] == x)
            return milieu;
        if (tab[milieu] > x)
            droite = milieu;
        else
            gau = milieu;
    }
    return -1;
}

int recherche(int tab[], int x, int size) {
    return rechercheBinaire(tab, x, 0, size - 1);
}

int main(int argc, char *argv[]) {
    argv++;
    argc--;

    const int val = atoi(argv[--argc]);

    int *a = (int *) malloc((argc) * sizeof(int));
    for (int i = 0; i < argc; i++)
        a[i] = atoi(argv[i]);

    const int trouve = recherche(a, val, argc);
    if (trouve != -1)
        printf("la valeur %i est à l'indice %i", val, trouve);
    else
        printf("valeur %i pas trouvée", val);
    free(a);
    return 0;
}