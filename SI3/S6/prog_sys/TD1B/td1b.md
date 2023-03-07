# TD1 - Bis - ProgSys

Nous avons choisi d'utiliser CMake au lieu d'un projet Visual Studio étant donné qu'il s'agit d'une meilleure solution dans l'ensemble.
En effet, CMake permet par exemple de faire des projets cross-platform, et ne dépend pas d'un IDE particulier. C'est le système de build standard pour C/C++ de nos jours

## Exercise 1

Il nous faut créer un fichier CMake à la racine :
```cmake=
cmake_minimum_required (VERSION 3.8)

# give a name to the project and use language C
project ("td1b" LANGUAGES C)

# not the cleanest way, but allows us to have the same output directory for exe, lib and dll
set (CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR})
set (CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR})

# Include sub-projects.
add_subdirectory ("Bibliotheques")
add_subdirectory("Exe_DynLink")
add_subdirectory("Exe_DynLoad")
```

Dans le dossier `Bibliotheques` :
```cmake=
# Generate a static library using the `printStop.c` file
add_library(bibliotheque STATIC printStop.c)
```

## Exercise 2

Dans le dossier `Exe_DynLink` :
```cmake=
# We create a dynamic library using the `printStop.c` file.
add_executable(Exe_DynLink main.c)
target_link_libraries(Exe_DynLink bibliotheque)
```
Comme prévu, le programme fonctionne sans le fichier `.lib`

## Exercise 3

On remplace le fichier CMake dans `Bibliotheques` par celui là :
```cmake=

add_library(bibliotheque SHARED printStop.c)
```
Au lieu de produire une bibliothèque statique, on produit maintenant une bibliothèque partagée. On doit donc garder le fichier `.dll` ou l'application ne fonctionnera pas.

## Exercise 4

Dans le dossier `Exe_DynLoad`:
```cmake=
add_executable(Exe_DynLoad main.c)
```
Voir `main.c` pour le code

## Exercice 5 : conclusion

Pour conclure, les bibliothèques Windows et Linux sont similaires mais différentes. Dans les deux cas, il existe une différence entre bibliothèques partagées et statiques.

Mais la ressemblance s'arrête là. Pour Windows, il faut exporter manuellement les fonctions, alors que Linux les exporte toutes par défaut.
De plus, sur Windows, la plupart des programmes viennent tous avec leurs DLLs, là où les programmes Linux se reposent sur les bibliothèques systèmes. Les DLLs Windows sont donc presque "statiques", étant donné qu'elle sont fournies avec chaque programme.
