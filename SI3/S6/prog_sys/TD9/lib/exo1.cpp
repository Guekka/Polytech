#include "exo1.h"

#include <csignal>

JNIEXPORT jint JNICALL Java_exo1_nativePid(JNIEnv *, jclass)
{
    return getpid();
}