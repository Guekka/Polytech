ProgSys - TD4
===

# Exercice 1 : `tres_simple`

Il y a plusieurs choses que nous pouvons remarquer dans ce programme :
- nous utilisons `pthread_create` pour créer un thread, qui exécutera `fn_thread`
- `pthread_create` prend en paramètre un pointeur vers une fonction de signature `void *(void *)`. Il faut donc effectuer des *casts*
- nous attendons que le thread ait terminé son exécution avec `pthread_join`

Lorsqu'on exécute le code tel quel, le résultat est bien celui qu'on attend. Mais si on enlève `join`, l'ordre d'exécution devient indéterminé. On se retrouve même parfois avec des strings affichées en double, par exemple :
```
Valeur renvoyée: 4096
The end
Hello from the thread
Hello from the thread
```
Sans compter la valeur de retour qui subit un comportement indéfini.

On peut donc en déduire que l'exécution d'un thread se déroule en parallèle du thread principal, sauf si le thread principal décide de l'attendre.

Si le thread principal se termine, tous les autres threads seront tués sans leur laisser le temps de terminer.

Si on ajoute `exit` dans un thread, cela termine toute l'application, y compris le `thread` principal. Alors que `pthread_exit` termine uniquement le thread courant.

# Exercice 2 : `threads`

Voir `threads.c`.

# Exercice 3 : `threads` avec API Linux

On constate en effet que l'ID du thread est différent.

# Exercice 4 : `multiple_fork`

Lançons le programme plusieurs fois :

| NPROCESS | temps (ms) | fonctionnel |
| --- | --- | --- |
| 100 | 3 | oui |
| 1000 | 30 | oui |
| 10000 | 380 | oui |
| 15000 | 458 | oui |
| 20000 | x | non |

Sur mon ordinateur, je suis donc limité aux alentours de 15000 processus. Le temps de création est assez important.

# Exercice 5 : `multiple_threads`

Lançons le programme plusieurs fois :

| NPROCESS | temps (ms) | fonctionnel |
| --- | --- | --- |
| 100 | 1 | oui |
| 1000 | 15 | oui |
| 10000 | 207 | oui |
| 15000 | 265 | oui |
| 20000 | x | non |

Sur mon ordinateur, je suis donc limité aux alentours de 15000 threads. Le temps de création est entre 2 et 3 fois plus faible qu'avec des processus.

Nous pourrions également comparer avec les coroutines, mais POSIX ne fournit pas d'implémentation.

# Exercice 6 : `htop`

Nous constatons en efft que chaque thread a un PID différent d'après htop, tout comme chaque processus.

D'ailleurs, en lançant plusieurs fois le programme à la suite, les PID continuent à grandir. Ne sont-ils pas réutilisés ?

D'après un post StackOverflow, oui. Mais ce même poste dit que la limite est environ 32 000, alors que j'ai atteint 150 000. Je suppose que la limite a été changée, et que les PID seront recyclés une fois la limite atteinte.

# Exercice 7 : `juste_presque`

Note : j'ai supposé une erreur dans le code. Pour moi, chaque thread devrait indiquer un nombre différent. Pour cela, j'ai "corrigé" le problème en augmentant le `sleep` dans la boucle de 1 à 7 secondes.

Ce programme affiche, dans l'ordre, "Dans la thread i" avec i allant de 0 à 4.

Si on enlève le `sleep` dans la boucle principale,  les threads affichent alors un nombre qui semble aléatoire ? Presque toujours 0, rarement 4.

En réalité, l'explication est simple. les threads se réfèrent à la variable `i` de la boucle. Celle-ci se terminant trop rapidement, on passe à la suite du code, qui met `i` à 0.

On peut le vérifier en augmentant le nombre de threads. Par exemple, avec 500 threads, on constate qu'il y a une grande variance : dans mon cas, beaucoup de 0, des 8, et des nombres plus ou moins dans l'ordre entre 250 et 500.

Évidemment, il n'est pas correct de faire dépendre un programme d'une condition d'attente. Pour ce cas là, il nous suffit de stocker l'argument du thread dans un endroit où il ne sera pas modifié : voir `juste.c`.

# Exercice 8 : `jeu`

Lorsqu'on enlève le `sleep`, on constate que :
- Mon ordinateur ne répond plus
- Les joueurs ne respectent pas l'ordre des tours

Cette consommation de ressources est normale, relativement au code : on a une boucle infinie qui fait des opérations sans pause. Forcément, le programme consommera toutes les ressources CPU disponibles.

# Exercice 9 : `fork_exec` sur Windows

Voir `fork_exec.c`

# Exercice 10 : `win_thread`

Voir `win_thread.c`

# Exercice 11 : synthèse

> Que préférez-vous ? L’approche Posix avec « fork + exec » ou bien l’approche Win32 avec « CreateProcess » ?

L'approche de Windows paraît plus logique. Si je veux exécuter un nouveau processus, il est plus logique d'avoir une seule fonction qui permet de faire cela. La possibilité de restreindre les droits sur Windows est aussi appréciable, même si cela rend l'API complexe.

> Et pour les threads, quelle approche en Posix et sa mise en œuvre sous Linux ou Win32 vous semble la plus adaptée et pourquoi ?

Sous Windows, le fait d'avoir un seul type `HANDLE` est assez perturbant. Je préfère avoir plusieurs types indépendants et être certain de ce que je manipule.

Au niveau de l'OS, il est tout de même beaucoup plus logique d'utiliser les threads comme unité, et non pas les processus comme sur POSIX. 
