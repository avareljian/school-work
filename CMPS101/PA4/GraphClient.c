#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"Graph.h"

int main(int argc, char* argv[]){		
		Graph A = newGraph(100);
		List L = newList();
		
		addArc(A, 64, 4);
        addArc(A, 64, 3);
        addArc(A, 42, 2);
        addArc(A, 2, 64);
        addArc(A, 4, 2);
        addArc(A, 3, 42);
        BFS(A, 3);
        getPath(L, A, 64);
		printf("getPath says path from 3-64 is: ");
		printList(stdout, L);
		printf("\n");
		printf("Path should be: 3 42 2 64\n");
	
		makeNull(A);
		clear(L);
		
		addEdge(A, 64, 4);
        addEdge(A, 64, 3);
        addEdge(A, 42, 2);
        addEdge(A, 2, 64);
        addEdge(A, 4, 2);
        addEdge(A, 3, 42);
        BFS(A, 3);
        getPath(L, A, 64);
		printf("getPath says path from 3-64 is: ");
		printList(stdout, L);
		printf("\n");
		printf("Path should be: 3 64\n");
		
		makeNull(A);
		clear(L);
		
		addEdge(A, 64, 4);
        addEdge(A, 64, 3);
        addEdge(A, 42, 2);
        addEdge(A, 2, 64);
        addEdge(A, 4, 2);
        addEdge(A, 3, 42);
        BFS(A, 64);
		printf("getDist says distance from 2-64 is: %d\n", getDist(A, 2));
		printf("Distance should be: 1\n");
		BFS(A, 4);
		printf("getDist says distance from 42-4 is: %d\n", getDist(A, 42));
		printf("Distance should be: 2\n");
		
	return(0);
}