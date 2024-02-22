# Rapport TP3

Dans ce TP, j'ai comparé les performances du l'algorithme de prefix sum avec 3 implémentations différentes:
séquentielle, parallèle avec OpenMP et parallèle avec la bibliothque standard C++ (STL).

J'ai également pris en compte l'impact de la taille du tableau et de l'activation des optimisations du compilateur.

## Résultats

Tous les résultats sont en secondes, mesurés sur un processeur Ryzen 4600H avec gcc version 13.2.0

La commande utilisée pour compiler le programme est la suivante:

```sh
g++ -std=c++20 -fopenmp -o prefix_sum prefix_sum.cpp
```

Sans optimisations, les performances sont les suivantes:

| Taille du tableau | Séquentielle | OpenMP | STL   |
|-------------------|--------------|--------|-------|
| 10^5              | 0.023        | 0.005  | 0.001 |
| 10^6              | 0.173        | 0.049  | 0.014 |
| 10^7              | 2.052        | 0.347  | 0.166 |
| 10^8              | 23.858       | 3.847  | 1.763 |

En activant `-O3`, les performances sont les suivantes:

| Taille du tableau | Séquentielle | OpenMP | STL    |
|-------------------|--------------|--------|--------|
| 10^5              | 0.001        | 0.006  | <0.000 |
| 10^6              | 0.031        | 0.152  | 0.001  |
| 10^7              | 0.290        | 0.225  | 0.041  |
| 10^8              | 3.572        | 2.630  | 0.428  |

## Conclusion

Sans surprise, la version de la STL dépasse de loin les autres implémentations.

On constate également que l'activation des optimisations du compilateur a un impact significatif sur les performances.
Notamment, l'écart entre séquentiel et OpenMP se réduit considérablement.

A moins d'avoir une raison particulière de ne pas utiliser la STL, il est recommandé de l'utiliser pour ce genre d'
opérations.

Egalement, la taille du tableau renverse l'ordre des performances: OpenMP est plus rapide que la version séquentielle,
mais seulement à partir de 10^7 données.
Il serait donc intéressant de choisir l'implémentation en fonction de la taille du tableau.


