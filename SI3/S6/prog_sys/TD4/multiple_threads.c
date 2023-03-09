#include "timer.h"

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

#define NPROCESS 15000

void wait_threads(pthread_t*, int n); // Définition du prototype de fonction pour l'utiliser

void *fn_thread(void *arg) {
    sleep(10);
    return NULL;
}

long create_threads(int n) {
    pthread_t *threads = malloc(n * sizeof(pthread_t));
	struct timespec vartime = timer_start(); /* Démarrage de la mesure temporelle */

    /* Création de n threads s'exécutant en parallèle */
    for (int i = 0; i < n; ++i) {
        pthread_create(&threads[i], NULL, fn_thread, NULL);
	}
	/* On mesure le temps écoulé pour la création des n thread */
	long time = timer_end(vartime);

    /* On attend pour pouvoir contaster la création des thread avec htop (mais ne compte pas dans le temps mesuré */
	sleep(10);

	/* On attend la fin des thread créés par mesure sociale et pour éviter un impact sur les prochaine mesures */
    wait_threads(threads, n);

	return time; /* Retourne le temps écoulé pour réaliser la création des n thread */
}

void wait_threads(pthread_t *threads, int n) {
    /* Mesure de salubrité sociale, le père attend la fin de tous ses fils */
    for (int i = 0; i < n; ++i)
        pthread_join(threads[i], NULL);
}

int main(int argc, char *argv[]) {
	int n = NPROCESS;

    for (int i = 1; i < argc; i++) {
		if (argv[i][0] != '-') {
			n = atoi(argv[i]);
		}
	}
	
	long time_thread = create_threads(n);
	
	printf("Time taken for creating %d thread (nanoseconds): %ld\n", n, time_thread);
	printf("Time taken for creating %d thread (milliseconds): %ld\n", n, time_thread / NANO_TO_MILLI);

    /* On flushe la sortie standard */
    fflush(stdout);

    /* Fin du père */
    exit(0);
}

