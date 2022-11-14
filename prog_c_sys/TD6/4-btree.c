#include <malloc.h>
#include <stdio.h>

struct node
{
    int value;
    struct node *left;
    struct node *right;
};
typedef struct node *tree;

tree create_tree(int v)
{
    tree t   = malloc(sizeof(struct node));
    t->value = v;
    t->left  = NULL;
    t->right = NULL;

    return t;
}

// créer un nouvel arbre vide
tree create_empty_tree(void)
{
    return create_tree(-1);
}

// ajouter v dans l'arbre t. Cette fonction renvoie l'arbre dans lequel v a été ajouté.`{.c}
tree add_tree(tree t, int v)
{
    if (t->value == -1) // empty tree
    {
        t->value = v;
        return t;
    }

    tree *next = &t->left;
    if (v > t->value)
        next = &t->right;

    if (*next == NULL)
    {
        *next = create_tree(v);
        return t;
    }
    else
        *next = add_tree(*next, v);

    return t;
}

// afficher l'arbre t (dans l'ordre)
void print_tree(tree t)
{
    if (t == NULL)
        return;
    printf("%d ", t->value);
    print_tree(t->left);
    print_tree(t->right);
}

// Renvoyer l'arbre de racine v (NULL si absent)
tree find_tree(tree t, int v)
{
    if (t == NULL)
        return NULL;

    if (t->value == v)
        return t;
    if (t->value > v)
        return find_tree(t->left, v);

    return find_tree(t->right, v);
}

// Libérer l'espace occupé par t
void free_tree(tree t)
{
    if (t == NULL)
        return;
    free_tree(t->left);
    free_tree(t->right);
    free(t);
}

int main()
{
    tree t = create_empty_tree();
    // Création de l'arbre du sujet
    t = add_tree(t, 8);
    t = add_tree(t, 3);
    t = add_tree(t, 10);
    t = add_tree(t, 1);
    t = add_tree(t, 6);
    t = add_tree(t, 7);
    t = add_tree(t, 4);
    t = add_tree(t, 14);
    t = add_tree(t, 13);
    // Impression de l'arbre (trié)
    printf("Valeurs dans l'arbre: ");
    print_tree(t);
    printf("\n");
    // Recherches
    printf("\nRecherches:\n");
    for (int i = 0; i < 20; i++)
    {
        printf("%2d: %s, ", i, find_tree(t, i) ? "oui" : "non");
        if (i % 5 == 4)
            printf("\n");
    }
    // Free
    printf("\nLibération mémoire de l'arbre:\n");
    free_tree(t);
    return 0;
}