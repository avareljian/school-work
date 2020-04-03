/****************************************************************************************
* FindComponents.c
* Client file of Graph ADT. Finds the strongly connected components of directed graphs.
* pa5
* Alex Vareljian
* avarelji
****************************************************************************************/
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"Graph.h"

int main(int argc, char* argv[]){
	FILE *in;
	FILE *out;
	int n = 0; // number of vertices
	int count = 0; // int to store number of connected components
	int v1; // tmp variable used for reading in file
	int v2; // tmp variable used for reading in file	
	List S = newList(); // list for controling main loop of DFS & maintaining stack of the order of finshed vertices
	List R = newList(); // stack for reading the connected components in the correct order

	//check command line for correct number of arguments
	if(argc !=3){
		printf("Usage: %s <input file> <output file>\n", argv[0]);
		exit(EXIT_FAILURE);
	}
	
	//open in file for reading
	in = fopen(argv[1], "r");
	if(in == NULL){
		printf("Unable to read from file %s\n", argv[1]);
		exit(EXIT_FAILURE);
	}

	//open output file for writing
	out = fopen(argv[2], "w");
	if(out == NULL){
		printf("Unable to write to file %s\n", argv[2]);
		exit(EXIT_FAILURE);
	}
	
	//MAKE THE GRAPH
	//read the first integer from the input file and create the graph
	fscanf(in, "%d", &n);
	Graph G = newGraph(n);
	//continue reading the rest of the ints by pairs,
	//adding them to the graph as it goes
	while(1){
		fscanf(in, "%d", &v1);
		fscanf(in, "%d", &v2);
		
		if( (v1 == 0) && (v2 == 0) ){
			break;
		}
		addArc(G, v1, v2);
	}
	fprintf(out, "Adjacency list representation of G:\n");
	printGraph(out, G);
	fprintf(out, "\n\n");
	
	// fill the list with ints 1-n to control main loop of DFS
	for( int i = 1; i <= n; i++ ){
		append(S, i);
	}
	
	DFS(G, S);
	
	/*fprintf(stdout, "\n Graph G table is: \n");
	fprintf(stdout, "x:  d  f  p\n");
	for(int i=1; i<=n; i++){
		fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(G, i), getFinish(G, i), getParent(G, i));
	}
	fprintf(stdout, "\n");
	printList(stdout, S);
	fprintf(stdout, "\n");
	*/
	Graph Gt = transpose(G);
	
	DFS(Gt, S);
	
	/*fprintf(stdout, "\n Graph Gt table is:\n");
	fprintf(stdout, "x:  d  f  p\n");
	for(int i=1; i<=n; i++){
		fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(Gt, i), getFinish(Gt, i), getParent(Gt, i));
	}
	fprintf(stdout, "\n");
	printList(stdout, S);
	fprintf(stdout, "\n");
	*/
	// count the strongly connected components
	for( moveFront(S); index(S) >= 0; moveNext(S) ){
		int temp = get(S);
		if( getParent(Gt, temp) == NIL ){
			count++;
		}
	}
	
	fprintf(out, "G contains %d strongly connected components:\n", count);
	moveBack(S);
	for( int i = 1; i<= count; i++ ){
		while(1){
			int temp = get(S);
			/*fprintf(stdout, " Reading temp %d\n", temp);*/
			prepend(R, temp);
			
			if( getParent(Gt, temp) == NIL ){
				movePrev(S);
				break;
			}
			movePrev(S);
		}
		fprintf(out, "Component %d: ", i);
		printList(out, R);
		fprintf(out, "\n");
		clear(R);
	}
	
	freeList(&S);
	freeList(&R);
	freeGraph(&G);
	freeGraph(&Gt);
	fclose(in);
	fclose(out);
}	