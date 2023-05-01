#include <stdio.h>
#include "HelloWorld.h" 

JNIEXPORT void JNICALL Java_HelloWorld_printCpp(JNIEnv *, jclass) {
        printf("World !\n");
}
