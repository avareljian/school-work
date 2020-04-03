#--------------------------------------------------------------------------------------------------
# Alexandra Vareljian
# avarelji
# Makefile for pa1
#--------------------------------------------------------------------------------------------------

JAVASRC    = Lex.java ListClient.java Node.java List.java
SOURCES    = README Makefile $ (JAVASRC)
MAINCLASS  = Lex
CLASSES    = Lex.class ListClient.class Node.class List.class
JARFILE    = Lex.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest
	
$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)