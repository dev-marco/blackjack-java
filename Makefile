JC = javac
JVM = java
CLASSES = */*.java
.SUFFIXES: .java .class
.java .class:
	$(JC) $*.java
MAIN = Main.BlackJack

default:
	@echo "Compilando..."
	@$(JC) $(CLASSES)
	@echo "Ok."

run:
ifeq ($(wildcard Main/BlackJack.class),)
	@$(MAKE) default
endif
	@$(JVM) $(MAIN) -c

nocolor:
ifeq ($(wildcard Main/BlackJack.class),)
	@$(MAKE) default
endif
	@$(JVM) $(MAIN)

.PHONY: clean

clean:
	$(RM) $(CLASSES:.java=.class)