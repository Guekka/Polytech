#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct Node
{
  char* str;
  struct Node* next;
} Node;

typedef struct Matrice
{
  double* array;
  int n;
  int m;
} Matrice;

Matrice* Matrice_create(int n, int m)
{
  Matrice* matrice = malloc(sizeof(Matrice));
  if (matrice == NULL)
  {
    return NULL;
  }

  matrice->array = calloc(n * m, sizeof(double));
  if (matrice->array == NULL)
  {
    return NULL;
  }

  matrice->n = n;
  matrice->m = m;
  return matrice;
}

void Matrice_free(Matrice* matrice)
{
  free(matrice->array);
  free(matrice);
}

double Matrice_get(Matrice* matrice, int i, int j) //récup le coeff i,j de la matrice
{
  return matrice->array[ j*matrice->n + i ];
}

void Matrice_set(Matrice* matrice, int i, int j, double valeur)
{
  matrice->array[ j*matrice->n + i ] = valeur;
}

typedef struct List
{
  Node* first;
  Node* end;
  size_t size_list;
} List;

List* List_create(void)
{
  List* list = malloc(sizeof(List));
  if (list == NULL)
  {
    return NULL;
  }

  list->first = NULL;
  list->end = NULL;
  list->size_list = 0;
  return list;
}

int List_push_back(List* list, const char* str)
{
  Node* new = malloc(sizeof(Node));
  if (new == NULL)
  {
    return -1;
  }

  new->str = malloc(sizeof(char) * (strlen(str) +1));
  if (new->str == NULL)
  {
    free(new);
    return -1;
  }
  strcpy(new->str, str);

  new->next = NULL;
  if (list->end != NULL)
  {
    list->end->next = new;
  }
  else
  {
    list->first = new;
  }
  list->end = new;
  list->size_list++;
  return 0;
}

void List_free(List* list)
{
  Node* node = list->first;
  while (node != NULL)
  {
    Node* tmp = node->next;
    free(node->str);
    free(node);
    node = tmp;
  }
  free(list);
}

bool isSpecialChar(char c)
{
  char specialChar[] = {'(', ')', '[', ']', ';', ',', '+', '-', '*', '/', '\\', '&', '|', '=', '.', '<', '>', '^', '\'', '\"', '{', '}', '#', '?', '!'};
  for (size_t i = 0; i < sizeof(specialChar) / sizeof(char); i++)
  {
    if (c == specialChar[i])
    {
      return true;
    }
  }
  return false;
}

bool isSpace(char c)
{
  char espace[] = {'\t', ' ', '\r'};
  for (size_t i = 0; i < sizeof(espace) / sizeof(char); i++)
  {
    if (c == espace[i])
    {
      return true;
    }
  }
  return false;
}

int push_back_str(char** str, char value, size_t *size, size_t *size_max)
{
  if (*size >= (*size_max)-1)
  {
    char* tmp = *str;
    *str = realloc(*str, sizeof(char) * (*size_max) * 2);
    if (*str == NULL)
    {
      *str = tmp;
      return -1;
    }
    *size_max *= 2;
  }
  (*str)[*size] = value;
  (*str)[*size+1] = '\0';
  (*size)++;
  return 0;
}

char* read_line_and_convert(FILE* file)
{
  char char_file = fgetc(file);
  if (char_file == EOF)
  {
    return NULL;
  }

  size_t size_max_str = 32;
  size_t size_str = 0;
  char* line = calloc(32, sizeof(char));
  if (line == NULL)
  {
    return NULL;
  }


  bool comment = false;
  bool word = false;
  bool chaine = false;

  while(char_file != '\n' && char_file != EOF && char_file != '\0')
  {

    if (char_file == '\"') // teste si on est dans une chaine de caractères
    {
      if (chaine)
      {
        chaine = false;
      }
      else
      {
        chaine = true;
      }
    }
    else if (chaine)
    {
      goto END_WHILE;
    }

    if (char_file == '/') // Teste si c'est un commentaire
    {
      if (comment)
      {
        break;
      }
      else
      {
        comment = true;
        word = false;
        goto END_WHILE;
      }
    }
    else if (comment == true)
    {
      comment = false;
      push_back_str(&line, '/', &size_str, &size_max_str);
    }

    if (isSpecialChar(char_file)) // teste si c'est un caractère pas alphanumérique
    {

      push_back_str(&line, char_file, &size_str, &size_max_str);
      word = false;
    }
    else if (isSpace(char_file))
    {
      word = false;
    }
    else if (!word)
    {
      word = true;
      push_back_str(&line, 'w', &size_str, &size_max_str);
    }

  END_WHILE:
    char_file = fgetc(file);
  }

  return line;
}

List* filetolist(const char* path)
{
  FILE* file = fopen(path, "r");
  if (file == NULL)
  {
    return NULL;
  }

  List* list_line = List_create();
  if (list_line == NULL)
  {
    fclose(file);
    return NULL;
  }

  char* line = NULL;

  while ((line = read_line_and_convert(file)) != NULL)
  {
    if (strlen(line) != 0)
    {
      if (List_push_back(list_line, line) != 0)
      {
        free(line);
        goto ERROR_FREE_LIST;
      }
    }
    free(line);
    line = NULL;
  }

  fclose(file);
  return list_line;

ERROR_FREE_LIST:
  List_free(list_line);
  fclose(file);
  return NULL;
}

int nb_digrammes(const char* str)
{
  size_t size = strlen(str);
  return (size < 2) ? size : size -1;
}

int nb_similarity(const char* str1, const char* str2)
{
  int count = 0;
  if (*(str1+1) == '\0' && *(str2+1) == '\0')
  {
    return *str1 == *str2;
  }
  for (; *(str1+1) != '\0'; str1++)
  {
    for (const char* str2_tmp = str2; *(str2_tmp +1) != '\0'; str2_tmp++)
    {
      if ( *str1 == *str2_tmp && *(str1+1) == *(str2_tmp +1) )
      {
        count++;
      }
    }
  }
  return count;
}

Matrice* pgm_creation(List *list1, List *list2) // prend en entrée deux listes de noeuds et crée une matrice de similarité entre les chaînes de caractères contenues dans ces listes
{
  Matrice* matrice = Matrice_create(list1->size_list, list2->size_list);
  if (matrice == NULL)
  {
    return NULL;
  }

  FILE *pgmfile = fopen("dice.pgm", "w+");
  if (pgmfile == NULL)
  {
    goto ERROR_FREE_MATRICE;
  }

  Node* ptr_list1 = list1->first;
  Node* ptr_list2 = list2->first;

  for (int i = 0; ptr_list1 != NULL; ptr_list1 = ptr_list1->next, i++)
  {
    ptr_list2 = list2->first;
    for (int j = 0; ptr_list2 != NULL; ptr_list2 = ptr_list2->next, j++)
    {
      int const similarity = nb_similarity(ptr_list1->str, ptr_list2->str);

      int const sum_digrammes = nb_digrammes(ptr_list1->str) + nb_digrammes(ptr_list2->str);

      Matrice_set(matrice, i, j, 1. - (2. * (double)similarity) / (double)sum_digrammes);
    }
  }

  fprintf(pgmfile, "P2\n");
  fprintf(pgmfile, "%d %d\n", matrice->n, matrice->m);
  fprintf(pgmfile,"1\n");

  for (int i = 0; i < matrice->n; i++)
  {
    for (int j = 0; j < matrice->m; j++)
    {
      fprintf(pgmfile, "%f ", Matrice_get(matrice, i, j));
    }
    fprintf(pgmfile, "\n");
  }
  fclose(pgmfile);
  return matrice;

ERROR_FREE_MATRICE:
  Matrice_free(matrice);
  return NULL;
}

int main(int argc, char **argv)
{   if (argc != 3) // vérifie s'il y a bien 2 arguments
  {
    printf("Ecrivez : %s <fichier1.txt> <fichier2.txt>\n", argv[0]);
    return 1;
  }

  const char* nomFichier1 = argv[1];
  const char* nomFichier2 = argv[2];

  List* list1 = filetolist(nomFichier1);
  List* list2 = filetolist(nomFichier2);

  Matrice* matrice_distance = pgm_creation(list1, list2);

  Matrice_free(matrice_distance);
  List_free(list1);
  List_free(list2);

  return 0;
}
