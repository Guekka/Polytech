#---------------------------------------------------------------------------------
# Variables
#---------------------------------------------------------------------------------

JAVA=java
JAVAC=javac
JFLAGS=-g

.SUFFIXES: .java .class

# ----------------------------------------------------------------------
# Code Java

SRC=$(sort $(wildcard *.java))
CLS=$(SRC:.java=.class)
H_FILE=$(SRC:.java=.h)
TARGET=$(SRC:.java=)
C_JNI=./lib

# ----------------------------------------------------------------------
# Code C/C++
CPP=g++
CXXFLAGS=-Wall -I$(JAVA_HOME)/include/ -I$(JAVA_HOME)/include/linux -L$(JAVA_HOME)/lib/server
CPP_SRC=$(sort $(wildcard *.cpp))
EXE=$(CPP_SRC:.cpp=.exe)

# ----------------------------------------------------------------------
# Règles implicites
%.class: %.java
	$(JAVAC) $(JFLAGS) $^

%.exe: %.cpp
	$(CPP) $(CXXFLAGS) -o $@ $^ -ljvm

%.h: %.java
	$(JAVAC) -h $(C_JNI) $^

#---------------------------------------------------------------------------------
# Cibles
#---------------------------------------------------------------------------------

all: $(CLS) $(H_FILE) $(EXE)
	$(MAKE) -C $(C_JNI)

clean:
	rm -rf $(CLS) $(C_JNI)/*.h $(EXE) *.so \#* *~
	$(MAKE) -C $(C_JNI) clean

test:
	LD_LIBRARY_PATH=$(LD_LIBRARY_PATH):$(C_JNI) $(JAVA) $(TARGET)
