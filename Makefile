# Compiler to use
JAVAC = javac

# Flags to pass to the compiler
JAVAC_FLAGS = -g

# Default target to build
all: build

# Target to build the program
build:
	$(JAVAC) $(JAVAC_FLAGS) src/s40192120_detector.java

# Target to run the program
run:
	java -cp src s40192120_detector '$(FILE1)' '$(FILE2)'

# Target to clean up the build
clean:
	rm -rf src/*.class