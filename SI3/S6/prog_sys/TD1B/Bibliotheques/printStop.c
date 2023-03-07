#include <stdio.h>

__declspec(dllexport) void printStop(char* s) {
	printf(s);
	getchar();
}