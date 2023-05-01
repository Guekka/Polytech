# Compilateur et options
# ----------------------

CC=g++
CXXFLAGS=-fpic -Wall -I$(JAVA_HOME)/include/ -I$(JAVA_HOME)/include/linux

TARGET=libHelloWorld.so
SRC=$(wildcard *.cpp)
OBJ=$(SRC:.cpp=.o)
H_FILE=$(SRC:.cpp=.h)

#---------------------------------------------------------------------------------
# Cibles
#---------------------------------------------------------------------------------
all: $(TARGET)

$(TARGET): $(OBJ)
	$(CC) -Wl,-soname,$@ -shared -o $@ $< 

%.o: %.cpp %.h
	$(CC) $(CXXFLAGS) -o $@ -c $<

clean:
	rm -f $(TARGET) $(OBJ) \#* *~
