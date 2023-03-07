#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
    if (argc != 2) {
        printf("Invalid arguments");
        exit(1);
    }
    char *input = argv[1];
    printf("Read %s\n", input);
    char *err = NULL;
    long num = strtol(input,& err, 10);
    if (err == input){
        printf("Invalid number");
        exit(1);
    }

    printf("Abs is: %li" , num > 0 ? num : -num );
}