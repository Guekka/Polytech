#include <dirent.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

int main(int argc, char **argv)
{
    // Open a file
    int fd   = open("tmp_file.txt", O_WRONLY | O_CREAT | O_TRUNC, 0644);
    DIR *dir = opendir(".");
    struct dirent *entry;

    // Fork
    switch (fork())
    {
        case -1:
            // Error
            break;
        case 0:
            // In the child, close the file
            write(fd, "This is the child.", 18);
            close(fd);

            // List the directory

            while ((entry = readdir(dir)) != NULL)
            {
                printf("%s", entry->d_name);
            }
            break;
        default:
        {
            // In the parent, write to the file
            write(fd, "Hello", 5);
            close(fd);

            while ((entry = readdir(dir)) != NULL)
            {
                printf("%s", entry->d_name);
            }
            break;
        }
    }
}
