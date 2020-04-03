#--------------------------------------------------------------------------------------------------
# Makefile for HW2
#--------------------------------------------------------------------------------------------------

JAVASRC    = ChessBoard.java ChessPiece.java Queen.java King.java Rook.java Bishop.java Knight.java Node.java
SOURCES    = README Makefile $ (JAVASRC)
MAINCLASS  = ChessBoard
CLASSES    = ChessBoard.class ChessPiece.class Queen.class King.class Rook.class Bishop.class Knight.class Node.class
JARFILE    = ChessBoard.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest
	
$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)
