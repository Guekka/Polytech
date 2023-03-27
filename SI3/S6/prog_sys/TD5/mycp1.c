#include "util.h"

#include <fcntl.h>
#include <malloc.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

void copy_file(const char *old, const char *new)
{
    int fd  = open(old, O_RDONLY);
    int fd2 = open(new, O_WRONLY | O_CREAT, 0644);
    char buf[1024];
    ssize_t n;

    while ((n = read(fd, buf, 1024)) > 0)
        if (write(fd2, buf, n) != n)
            perror("write");

    close(fd);
}

void copy_to_dir(int file_count, char *files_then_dir[])
{
    const char *dir = files_then_dir[file_count];
    for (int i = 0; i < file_count; i++)
    {
        const char *file                  = files_then_dir[i];
        const unsigned long new_name_size = strlen(dir) + strlen(file) + 2;
        char *new_name                    = malloc(new_name_size);
        snprintf(new_name, new_name_size, "%s/%s", dir, file);
        copy_file(file, new_name);
        free(new_name);
    }
}

bool source_are_only_files(int file_count, char *files_then_dir[])
{
    for (int i = 0; i < file_count; i++)
    {
        if (is_dir(files_then_dir[i]))
            return false;
    }
    return true;
}

int main(int argc, char **argv)
{
    if (argc < 3)
        return 1;

    if (!source_are_only_files(argc - 2, argv + 1))
        return 1;

    if (argc == 3)
    {
        copy_file(argv[1], argv[2]);
        return 0;
    }
    else if (is_dir(argv[argc - 1]))
    {
        copy_to_dir(argc - 2, argv + 1);
    }
    else
    {
        return 1;
    }
}