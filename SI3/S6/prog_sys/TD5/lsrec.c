#include "util.h"

#include <dirent.h>
#include <fcntl.h>
#include <malloc.h>
#include <stddef.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>

void print_file_stat(int fd)
{
    struct stat st;
    fstat(fd, &st);
    printf("File size: %ld bytes ", st.st_size);
    printf("File inode: %ld ", st.st_ino);
    printf("File mode: %o ", st.st_mode);
    printf("File type: %s\n", S_ISDIR(st.st_mode) ? "directory" : "file");
}

int lsrec_fd(int dirfd, const char *prefix)
{
    DIR *dir = fdopendir(dirfd);

    if (dir == NULL)
    {
        perror("Error accessing dir");
        return 1;
    }

    // iterate recursively over the directory
    // and print the name of each file
    struct dirent *dentry = NULL;
    while ((dentry = readdir(dir)) != NULL)
    {
        printf("%s %s ", prefix, dentry->d_name);
        if (dentry->d_type & DT_DIR)
        {
            printf("\n");
            if (is_dot_dir(dentry->d_name))
            {
                continue;
            }

            const unsigned long prefix_size = strlen(prefix) + 3;
            char *new_prefix                = malloc(prefix_size);

            snprintf(new_prefix, prefix_size, "%s--", prefix);

            int new_fd = openat(dirfd, dentry->d_name, O_RDONLY | O_DIRECTORY);

            lsrec_fd(new_fd, new_prefix);
            free(new_prefix);
        }
        else
        {
            int file_fd = openat(dirfd, dentry->d_name, O_RDONLY);
            print_file_stat(file_fd);
        }
    }
    closedir(dir);
    return 0;
}

int lsrec(const char *dir)
{
    int dirfd = open(dir, O_RDONLY | O_DIRECTORY);
    return lsrec_fd(dirfd, "");
}
int main(int argc, char **argv)
{
    if (argc != 2)
        return 1;

    return lsrec(argv[1]);
}
