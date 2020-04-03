# Makefile for fileReverse.c

fileReverse : fileReverse.o
	gcc -o fileReverse fileReverse.o

hello.o : hello.c
	gcc -c -std=c99 -Wall fileReverse.o

clean :
	rm -f fileReverse fileReverse.o
