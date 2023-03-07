#include <stddef.h>
#include <memory.h>
#include <stdio.h>
#include <assert.h>

const int STOP_VALUE = (int) -1;

void print_array(int *array, size_t count);

/// array has to be big enough
void insertion(int *array, size_t *count, int value) {
    int where_to_put = 0;
    // find the location
    while (where_to_put < *count && array[where_to_put] <= value)
        ++where_to_put;

    // shift array by one
    memmove(array + where_to_put + 1, array + where_to_put, *count * sizeof(int));

    // insert at the vacant place
    array[where_to_put] = value;

    ++*count;
}

void print_array(int *array, size_t count) {
    printf("[ ");
    for (int *val = array; val < array + count; ++val)
        printf("%d ", *val);
    printf("]\n");
}

int input_integer() {
    int value;
    assert (scanf("%d", &value) != 0);
    return value;
}

int main() {
    // a safer approach would be dynamic allocation, but we've not gotten to this point yet
    const size_t ARRAY_LEN = 1000;
    int sorted_array[ARRAY_LEN];
    memset(sorted_array, 0, ARRAY_LEN);

    size_t array_count = 0;
    while (1) {
        int read_num = input_integer();
        if (read_num == STOP_VALUE)
            break;

        insertion(sorted_array, &array_count, read_num);
    }
    print_array(sorted_array, array_count);
}