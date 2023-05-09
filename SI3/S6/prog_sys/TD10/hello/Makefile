#---------------------------------------------------------------------------------
# Primitives de POSIX.1 : Manipulations de processus
#---------------------------------------------------------------------------------

#---------------------------------------------------------------------------------
# Variables
#---------------------------------------------------------------------------------

# Compilateur et options
# ----------------------
CC=gcc
CFLAGS=-std=gnu99 -Wall -Wextra -g 

# Pour tester la compilation avec la norme Ansi C des signaux
# CFLAGS=-Wall -Wextra -ansi -g

SRC=$(wildcard *.c)
OBJ=$(SRC:.c=.o)
EXE=$(SRC:.c=.exe)

#---------------------------------------------------------------------------------
# Cibles principales
#---------------------------------------------------------------------------------

# Exécutables
# -----------

all: $(EXE)
	@for dir in $(shell ls -d ../hello-*); do cp $^ $${dir}; done

# Règle par défaut pour construire foo.exe à partir d'un seul fichier foo.o
# -------------------------------------------------------------------------

hello.exe: hello.c
	@docker run --rm -v $(shell pwd):/home -w /home gcc:latest gcc -static -o $@ $^


# Nettoyages
# ----------

# Nettoyage léger
clean:
	@rm -f \#* *.o $(EXE)
	@for dir in $(shell ls -d ../hello-*); do rm -f $${dir}/*.exe; done

# Nettoyage en profondeur
mrproper: clean
	@rm -rf *~
