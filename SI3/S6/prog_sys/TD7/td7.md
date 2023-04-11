Prog Sys TD7
===

# 2. Manipulation élémentaire de signaux

1. Le terminal répond toujours, on peut écrire du texte, mais rien ne se passe
2. On reprend le contrôle du shell, on peut lancer des commandes mais xeyes ne répond plus
3. On conserve le shell et xeyes fonctionne
4. PID: 9529
5. Cette commande produit le même effet que Ctrl Z
6. (18) SIGCONT
7. pkill -9 xeyes

# 3.2 Signaux reçus pendant sleep()

Le premier signal reçu interrompt le sleep, et appelle correctement le handler.
> sleep() causes the calling thread to sleep either until the number of real-time seconds specified in seconds have elapsed or until a signal arrives which is not ignored.

# 3.3 Compilation avec l’option `-ansi` de gcc

On constate bien les différences prévues. sigaction ne compile pas, comme prévu.

# 3.4 Ignorer ? Bloquer ? Capturer sans rien faire ?

- Quand on ignore le signal, rien ne se passe
- Quand on masque le signal, rien ne se passe... jusqu'au démasquage, où le signal est reçu. Le handler n'est appelé qu'une seule fois, quel que soit le nombre de signaux
- Quand on le capture sans masque, le sleep est interrompu et le handler est appelé
- Quand on le capture sans handler, le programme est interrompu. On a donc bien un comportement par défaut.