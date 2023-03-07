#include <Windows.h>
#include <stdio.h>

typedef void(*printStop) (char *);
int main()
{
	void* hinstDLL = LoadLibrary("bibliotheque.dll");
	if (!hinstDLL) {
		printf("Impossible de charger la bibliothèque.");
		exit(1);
	}
	printStop printS = (printStop)GetProcAddress(hinstDLL, "printStop");
	
	printS("hello");
	FreeLibrary(hinstDLL);
}