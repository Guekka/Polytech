# Programmation système : TD1

## Exercice 1 : expliquer l'architecture du code fourni

Le code fourni réalise un tri d'une liste "aléatoire" (avec seed fixée).

Pour éviter de dépendre d'un seul algorithme de tri, la fonction `main` a uniquement accès au prototype de `sort(int *array, int size)`. Les implémentations sont fournies dans différents fichiers comme `merge.c` ou `bubble.c`.

Ce mécanisme permet de sélectionner l'implémentation à la compilation : il suffit de compiler le bon fichier. Si nous en compilons plusieurs en même temps, le *linker* émettra une erreur.

En pratique, cette manière de faire est assez limitée. Si les implémentations sont fixées, on utilisera plutôt des macros. Si l'utilisateur peut ajouter son implémentation, alors nous pourrons utiliser des bibliothèques dynamiques que nous verrons par la suite.

## Exercice 2 : dépendances de `basicExe`

```sh
$ ldd tri_bubble-basicExe.exe

        linux-vdso.so.1 (0x00007ffff7fc4000)
        libc.so.6 => /nix/store/lqz6hmd86viw83f9qll2ip87jhb7p1ah-glibc-2.35-224/lib/libc.so.6 (0x00007ffff7db5000)
        /nix/store/lqz6hmd86viw83f9qll2ip87jhb7p1ah-glibc-2.35-224/lib/ld-linux-x86-64.so.2 => /nix/store/lqz6hmd86viw83f9qll2ip87jhb7p1ah-glibc-2.35-224/lib64/ld-linux-x86-64.so.2 (0x00007ffff7fc6000)
```

Nous pouvons voir que nous dépendons de 3 bibliothèques dynamiques.

## Exercice 3 : comment rendre le programme indépendant ?

Le programme sera indépendant si on le compile avec des bibliothèques statiques. Pour cela, il nous suffit d'utiliser le paramètre `-static` de `gcc`.

Nous constatons que le programme utilisant des bibliothèques statiques est bien plus grand. C'est parfaitement logique : il inclut toutes les fonctions dont il a besoin (et même plus).

Il contient donc beaucoup plus de code.

## Exercice 4 : bibliothèque statique

Pour créer une bibliothèque statique et nous en servir, nous utilisons les commandes suivantes :
```sh
ar -r lib.a fichier.o
ranlib lib.a
gcc -o exe main.o lib.a
```
Nous constatons que cette version a une taille très légèrement inférieure a la première version, `basicExe`. En effet, le passage par une bibliothèque a permis à `gcc` de détecter que le fichier `unused.o` est inutilisé. Il ne sera donc pas inclus.

```sh
$ size tri_bubble-staticLib.exe
   text    data     bss     dec     hex filename
   3383     668      12    4063     fdf tri_bubble-staticLib.exe

$ size tri_bubble-basicExe.exe
   text    data     bss     dec     hex filename
   4822     668      12    5502    157e tri_bubble-basicExe.exe
```
La partie impactée est `text` qui contient les instructions.

```sh
$ diff <(nm -j tri_bubble-basicExe.exe) <(nm -j tri_bubble-staticLib.exe)
2,4d1
< bar
< bar.localalias
< baz
21d17
< foo
35d30
< qux
```
En effet, certains symboles ont bien été supprimés. Ils semblent correspondre à ceux présents dans `unused.c`.

## Exercice 5 : bibliothèque partagée

Pour créer une bibliothèque partagée et nous en servir, nous utilisons les commandes suivantes :
```sh
gcc -fpic -shared -o lib.so fichier.o
gcc -o exe main.o lib.so
```

## Exercice 6 : activation de tous les algorithmes

Voir `Makefile`.
