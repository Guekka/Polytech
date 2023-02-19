#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>

#include "sort.h"

void *dyn_handle = NULL;
void (*sortf)(int tab[], int size) = NULL;

void sort(int tab[], int size) {
    sortf(tab, size);
}

void load_library(const char *lib) {
    dyn_handle = dlopen(lib, RTLD_LAZY);
    if(!dyn_handle)
    {
        fprintf(stderr, "Error loading lib");
        exit(EXIT_FAILURE);
    }
    dlerror();

    *(void **) (&sortf) = dlsym(dyn_handle, "sort");

    const char* error = dlerror();
    if (error != NULL)
    {
        fprintf(stderr, "Error");
        exit(EXIT_FAILURE);
    }
}

void unload_library() {
    sortf = NULL;
    dlclose(dyn_handle);
    dyn_handle = NULL;
}