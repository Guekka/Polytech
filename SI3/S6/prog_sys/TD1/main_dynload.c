#include <stdlib.h>
#include <stdio.h>
#include <time.h>

#include "sort.h"
#include "utils.h"
#include "timer.h"

#include "unistd.h"

/* ------------------------------------------------------------------------------------
 * Valeurs par défaut des variables globales qui suivent 
 * ------------------------------------------------------------------------------------
 */

#define SIZE 5
#define FALSE 0
#define TRUE 1

/* ------------------------------------------------------------------------------------
 * Variables globales 
 * ------------------------------------------------------------------------------------
*/

static int Size_Array = SIZE;
static int Verbose = FALSE;

/* ------------------------------------------------------------------------------------
 * Prototypes de fonctions définies plus tard 
 * ------------------------------------------------------------------------------------
*/
static void Scan_Args(int argc, char *argv[]);
static void Usage(const char *execname);
void load_library(const char *lib);
void unload_library();

void do_job() {
    int list[Size_Array];

    // Initialize a ramdom list of numbers;
    srand(0);
    for (int i = 0; i < Size_Array; i++) {
        list[i] = rand() % 1000;
    }

	printf("Array to sort:");
	print_list(list, Size_Array);
	
    struct timespec vartime = timer_start();
    sort(list, Size_Array);
    long time_elapsed_nanos = timer_end(vartime);
	
    if (Verbose) {
		printf("Time taken for sorting (nanoseconds): ");
		printf("%ld\n", time_elapsed_nanos);
	}

	printf("Sorted array:");
	print_list(list, Size_Array);
}

int main(int argc, char *argv[])
{
    /* Décodage des arguments de la ligne de commande */
    Scan_Args(argc, argv);

    const char* libs[] = {
        "libTri_bubble-dynamicLib.so",
        "libTri_insertion-dynamicLib.so",
        "libTri_merge-dynamicLib.so",
        "libTri_quick-dynamicLib.so"
    };

    for(int i = 0; i < 4; ++i){
        if (Verbose) {
            printf("With lib: %s\n", libs[i]);
        }
        load_library(libs[i]);
	    do_job();
        unload_library();
    }
}

/* Analyse des arguments 
 * ----------------------
 */
static void Scan_Args(int argc, char *argv[])
{
    for (int i = 1; i < argc; i++) {
        if (argv[i][0] == '-') {
            switch (argv[i][1]) {
            case 'h':
                Usage(argv[0]);
                exit(1);
            case 's':
                Size_Array = atoi(argv[++i]);
                break;
			case 'v':
				Verbose = TRUE;
				break;
            }
        }
    }
}

/* Information d'utilisation
 * -------------------------
 */
static void Usage(const char *execname)
{
    printf("\nUsage: %s [options]\n", execname);
    printf("\nOptions générales\n"
           "-----------------\n"
           "-h\tce message\n"
           "-s\ttaille du tableau à trier"
		   "-v\taffiche plus d'informations");
}