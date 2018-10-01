# Makefile
# Nyeezy

SRCDIR = src
BINDIR = bin
DOCDIR = doc
TESTDIR = test

JAVAC = javac
JFLAGS = -g -d $(BINDIR) -cp $(BINDIR)

vpath %.java $(SRCDIR):$(TESTDIR)
vpath %.class $(BINDIR)

.SUFFIXES: .java	.class

.java.class:
	$(JAVAC)	$(JFLAGS) $<

all: Tree.class\
		Land.class\
		SunData.class\
		Simulation.class\
		ForestPanel.class\
		TreeGrow.class\

run:
	java -cp $(BINDIR) TreeGrow sample_in.txt

doc: all
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java

clean:
	@rm -f $(BINDIR)/*.class
	@rm -f doc/*
