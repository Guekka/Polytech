#ifndef VECTOR_H
#define VECTOR_H

#include <assert.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdlib.h>
#include <unistd.h>

typedef struct vector vector;

size_t vector_required_bytes(vector *vec);

void vector_grow(vector *vec);

void vector_push_back(vector *vec, VECTOR_TYPE element);

struct vector
{
    VECTOR_TYPE *data;
    size_t len;
    size_t capacity;
};

size_t vector_required_bytes(vector *vec)
{
    return vec->capacity * sizeof(VECTOR_TYPE);
}

void vector_grow(vector *vec)
{
    vec->capacity *= 2;
    vec->data = realloc(vec->data, vector_required_bytes(vec));
    assert(vec->data != NULL);
}

void vector_push_back(vector *vec, VECTOR_TYPE element)
{
    if (vec->len == vec->capacity)
        vector_grow(vec);

    vec->data[vec->len++] = element;
}

void vector_pop_back(vector *vec)
{
    --vec->len;
}

VECTOR_TYPE *vector_back(vector *vec)
{
    return &vec->data[vec->len - 1];
}

vector vector_make()
{
    const size_t default_capacity = 500;

    vector vec;
    vec.capacity = default_capacity;
    vec.len      = 0;
    vec.data     = NULL;

    vector_grow(&vec);

    return vec;
}

#endif // VECTOR_H
