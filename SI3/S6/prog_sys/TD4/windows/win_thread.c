#include <strsafe.h>
#include <tchar.h>
#include <windows.h>

DWORD MyThreadFunction(void* lpParam) {
    printf("Called with: %d\n", *(int*)lpParam);
    return 0;
}

int main() {
    int data = 1;
    DWORD dwThreadId;
    HANDLE hThread;

    // Create the thread to begin execution on its own.
    hThread = CreateThread(NULL,              // default security attributes
                           0,                 // use default stack size
                           MyThreadFunction,  // thread function name
                           &data,             // argument to thread function
                           0,                 // use default creation flags
                           &dwThreadId);      // returns the thread identifier

    if (hThread == NULL) {
        ExitProcess(1);
    }

    // Wait until thread has terminated.
    WaitForSingleObject(hThread, INFINITE);

    // Close all thread handles and free memory allocations.
    CloseHandle(hThread);

    return 0;
}
