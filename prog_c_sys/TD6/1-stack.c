#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define ELEMENT_TYPE int
#define ELEMENT_TYPE_STRING "%d"

typedef struct element
{
    ELEMENT_TYPE valeur;
    struct element *suivant;
} Element;

typedef struct stack
{
    Element *premier;
} Stack;

Stack init_stack(void)
{
    Stack p = {.premier = NULL};
    return p;
}

void push_item(Stack *stack, ELEMENT_TYPE value)
{
    assert(stack != NULL);

    Element *el = malloc(sizeof(Element));
    assert(el != NULL);

    el->valeur     = value;
    el->suivant    = stack->premier;
    stack->premier = el;
}

ELEMENT_TYPE pop_item(Stack *stack)
{
    assert(stack != NULL);
    assert(stack->premier != NULL);

    Element *popped = stack->premier;
    stack->premier  = popped->suivant;

    ELEMENT_TYPE val = popped->valeur;
    free(popped);
    return val;
}

ELEMENT_TYPE top_value(Stack *stack)
{
    assert(stack != NULL);
    assert(stack->premier != NULL);
    return stack->premier->valeur;
}

void print_stack(Stack *stack)
{
    assert(stack != NULL);
    Element *element = stack->premier;
    while (element != NULL)
    {
        printf(ELEMENT_TYPE_STRING "\n", element->valeur);
        element = element->suivant;
    }
}

int main()
{
    Stack stack = init_stack();

    // generate stack with 10 values
    for (int i = 0; i < 10; i++)
    {
        push_item(&stack, i);
    }

    // print stack
    print_stack(&stack);

    printf("Popping  values\n");

    // pop 5 values
    for (int i = 0; i < 5; i++)
    {
        pop_item(&stack);
    }

    // print stack
    print_stack(&stack);

    // print top value
    printf(ELEMENT_TYPE_STRING, top_value(&stack));

    return 0;
}