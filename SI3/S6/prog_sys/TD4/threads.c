#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/syscall.h>
#include <sys/types.h>


void *fn_thread(void *arg) {
  const int sleep_time = *(int*) arg;
  if(sleep_time < 0) {
    pthread_exit((void *) 1);
  }

  for(int i = 0; i < 5; ++i) {
      printf("thread pid: %d ; thread id: %lu\n", getpid(), pthread_self());
      printf("Linux Thread ID: %ld\n", syscall(SYS_gettid));
      sleep(sleep_time);
  }
    pthread_exit((void *) 0);
}

int main(int argc, char **argv)
{
    if (argc != 3)
        return 1;

    int n1 = atoi(argv[1]);
    int n2 = atoi(argv[2]);

    printf("root pid: %d\n", getpid());

    pthread_t th1, th2;
    pthread_create(&th1, NULL, fn_thread, &n1);
    pthread_create(&th2, NULL, fn_thread, &n2);

    int ret1, ret2;
    pthread_join(th1, (void*) &ret1);
    pthread_join(th2, (void*) &ret2);

    printf("Return values : %d %d", ret1, ret2);
}