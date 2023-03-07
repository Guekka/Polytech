# Exercice n°1 : `multiple_fork`

Voir `multiple_fork.c`.

Nous utilisons ici la fonction `fork()` pour créer un processus fils.

# Exercice n°2 : `multiple_fork` parallèle

Malgré l'appel de `sleep(1)` 100 fois, le temps d'exécution est de 10 secondes.

En effet, les processus fils sont bel et bien exécutés en parallèle. 
L'ordre d'exécution des processus fils est indéterminé, mais d'après mes tests, il semble que l'ordre soit presque respecté. Est-ce du à mon utilisation de `waitpid` dans l'ordre de création des processus fils ?

Les PID des processus fils sont successifs, ce qui est logique. En effet, le PID est incrémenté à chaque appel de `fork()`, et aucun processus n'a été créé entre deux appels de `fork()`.

# Exercice n°3 : `zombie`

Voir `zombie.c`.

Ici, le processus fils est créé et se termine, mais le processus père ne fait pas d'appel à `wait()` ou `waitpid()`. Le processus fils devient donc un zombie, jusqu'à ce que le processus père soit terminé.

La commande `ps aux` permet de voir les processus zombies : `ps aux | grep Z`.

# Exercice n°4 : `orphelin`

Voir `orphelin.c`.

Ici, le processus fils est créé et ne se termine pas. Le processus père se termine, et le processus fils devient donc un orphelin. Il est alors adopté par un processus fixé, ici `systemd`.

La commande `ps aux` permet de voir les processus orphelins : `ps aux | grep orphan`.

# Exercice n°5 : `exec_prop`

Voir `exec_prop.c` et `exec_prop_aux.c`.

Dans cet exercice, nous vérifions certaines propriétés de `fork(2)` et `exec(2)`.

- `fork(2)` crée un nouveau processus, qui est une copie du processus appelant.
- Il dispose donc d'un nouveau PID, et d'une copie des buffers E/S. Dans notre cas, nous pouvons voir que la chaîne du processus père est affichée à la fois par le processus père et par le processus fils.


- `exec(2)` remplace le processus appelant par un nouveau processus. Il n'y a donc plus de processus fils, mais un nouveau processus père qui conserve le PID.
- Les buffers E/S sont également remplacés. Nous pouvons donc voir que la chaîne du processus père n'est jamais affichée. Il faut donc faire attention à vider les buffers E/S avant d'appeler `exec(2)`.

# Exercice n°6 : `shell_exec`

Voir `shell_exec.c`.

Nous cherchons à reproduire le comportement du Shell. Nous créons donc un nouveau processus avec `fork`, qui va exécuter la commande passée en argument avec `exec`.

# Exercice n°7 : `shell_exec` avec `cd`

`cd` ne fonctionne pas avec `exec`, car `cd` modifie le processus appelant. Nous devons donc utiliser `chdir` pour changer le répertoire courant. 

Si on compare avec le Shell, on peut voir que notre programme ne change pas le répertoire courant du Shell. Nous nous trouvons donc dans le même répertoire après l'exécution de notre programme, alors que le Shell change de répertoire.
Cela est dû au fait que le Shell est un processus qui ne se termine jamais, et qui est ainsi toujours en cours d'exécution. Notre programme, lui, se termine après l'exécution de la commande.

# Exercice n°8 : `shell_system`

Voir `shell_system.c`.

Nous voulons reproduire le comportement d'un Shell. Nous créons donc un REPL (Read-Eval-Print-Loop) qui lit une commande, l'évalue et affiche le résultat. Nous utilisons `system` pour évaluer la commande.

# Exercice n°9 : `shell_system` sans `system`

Cette fois, nous devons implémenter notre propre version de `system`, sans utiliser `system` directement.

Nous créons donc un nouveau processus avec `fork`, qui va exécuter la commande passée en argument avec `exec`. Nous devons également gérer la séparation des arguments de la commande.

Nous choisissons la variante `execvp` pour exécuter la commande, qui permet de passer un tableau de chaînes de caractères en argument et cherche la commande dans le PATH. Nous devons donc séparer les arguments de la commande, et les stocker dans un tableau de chaînes de caractères.

Note : getline conserve le caractère de fin de ligne `\n` à la fin de la chaîne de caractères. Nous devons donc le remplacer par `\0` pour que la commande soit correctement exécutée.


Il est intéressant de constater la différence de comportement entre `system` et notre propre version de `system` lorsqu'on appuie sur `Ctrl+C` pendant l'exécution d'une commande : dans le premier cas, le REPL continue son exécution et la commande sous-jacente s'interrompt, alors que dans le second cas, le REPL est interrompu.