# Compilateur et options
# ----------------------

CC=g++
CXXFLAGS=-fpic -Wall -I$(JAVA_HOME)/include/ -I$(JAVA_HOME)/include/linux

TARGET=libHelloWorld.so libexo1.so

#---------------------------------------------------------------------------------
# Cibles
#---------------------------------------------------------------------------------
all: $(TARGET)

lib%.so: %.o
	$(CC) -Wl,-soname,$@ -shared -o $@ $< 

%.o: %.cpp %.h
	$(CC) $(CXXFLAGS) -o $@ -c $<

clean:
	rm -f $(TARGET) $(OBJ) \#* *~
