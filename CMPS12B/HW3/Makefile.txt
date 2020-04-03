#--------------------------------------------------------------------------------------------------
# Makefile for HW3
#--------------------------------------------------------------------------------------------------

JAVASRC    = ChessMoves.java ChessPiece.java Queen.java King.java Rook.java Bishop.java Knight.java Node.java
SOURCES    = README Makefile $ (JAVASRC)
MAINCLASS  = ChessMoves
CLASSES    = ChessMoves.class ChessPiece.class Queen.class King.class Rook.class Bishop.class Knight.class Node.class
JARFILE    = ChessMoves.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest
	
$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)