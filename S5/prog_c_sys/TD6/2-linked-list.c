#include <assert.h>
#include <stdbool.h>
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

typedef struct linked_list
{
    Element *premier;
} List;

List init_list(ELEMENT_TYPE value)
{
    Element *el = malloc(sizeof(Element));
    assert(el != NULL);
    el->valeur  = value;
    el->suivant = NULL;

    List p = {.premier = el};
    return p;
}

void insert_node_after(Element *el_before, int val)
{
    assert(el_before);

    Element *new_el = malloc(sizeof(Element));
    assert(new_el);

    new_el->valeur     = val;
    new_el->suivant    = el_before->suivant;
    el_before->suivant = new_el;
}

// Ajouter la valeur v à la fin de la liste lst
void append_element(List *lst, int value)
{
    assert(lst != NULL);

    Element *end = lst->premier;
    while (end->suivant != NULL)
    {
        end = end->suivant;
    }
    insert_node_after(end, value);
}

// Ajouter la valeur value dans la liste ordonnée lst
void insert_element_sorted(List *lst, int value)
{
    assert(lst != NULL);

    Element *pos = lst->premier;
    while (pos->suivant != NULL && pos->suivant->valeur < value)
    {
        pos = pos->suivant;
    }
    insert_node_after(pos, value);
}

void delete_node_after(Element *el_before)
{
    assert(el_before);

    if (el_before->suivant == NULL)
        return;

    Element *to_delete = el_before->suivant;
    el_before->suivant = to_delete->suivant;

    free(to_delete);
}

// Supprimer la (première) valeur égale à v de la liste lst
void delete_element(List *lst, int v)
{
    assert(lst != NULL);
    assert(lst->premier != NULL);

    Element *before_to_pop = lst->premier;
    while (before_to_pop->suivant != NULL)
    {
        if (before_to_pop->suivant->valeur == v)
        {
            delete_node_after(before_to_pop);
            return;
        }
        before_to_pop = before_to_pop->suivant;
    }
}

// Supprimer tous les éléments égaux à v dans la liste lst
void delete_elements(List *lst, int v)
{
    assert(lst != NULL);
    assert(lst->premier != NULL);

    Element *before_to_pop = lst->premier;
    while (before_to_pop->suivant != NULL)
    {
        printf("d: %d\n", before_to_pop->valeur);
        if (before_to_pop->suivant->valeur == v)
        {
            delete_node_after(before_to_pop);
        }
        else
        {
            before_to_pop = before_to_pop->suivant;
        }
    }
}

// Tester si la valeur v est dans liste lst
bool find_element(List *lst, int v)
{
    assert(lst != NULL);
    assert(lst->premier != NULL);

    Element *el = lst->premier;
    while (el != NULL)
    {
        if (el->valeur == v)
        {
            return true;
        }
        el = el->suivant;
    }
    return false;
}

void print_list(List *lst)
{
    assert(lst != NULL);
    Element *element = lst->premier;
    printf("[ ");
    while (element != NULL)
    {
        printf(ELEMENT_TYPE_STRING " ", element->valeur);
        element = element->suivant;
    }
    printf("]\n");
}

int main()
{
    List list = init_list(1);
    // tests all functions
    append_element(&list, 2);
    append_element(&list, 3);
    append_element(&list, 4);
    append_element(&list, 5);

    print_list(&list);

    insert_element_sorted(&list, 3);

    print_list(&list);

    printf("Deleting 2 and 3\n");

    delete_element(&list, 2);
    delete_elements(&list, 3);

    print_list(&list);

    printf("1 is in list:  %d\n", find_element(&list, 1));
    printf("2 is in list:  %d\n", find_element(&list, 2));
}