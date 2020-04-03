/****************************************************************************************
* FindPath.c
* Client file of Graph ADT
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
	int v1; // tmp variable used for reading in file
	int v2; // tmp variable used for reading in file	
	

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
	Graph myGraph = newGraph(n);
	//continue reading the rest of the ints by pairs,
	//adding them to the graph as it goes
	while(1){
		fscanf(in, "%d", &v1);
		fscanf(in, "%d", &v2);
		
		if( (v1 == 0) && (v2 == 0) ){
			break;
		}
		addEdge(myGraph, v1, v2);
	}
	printGraph(out, myGraph);
	fprintf(out, "\n");
	
	//RUN BFS ON THE PROVIDED SOURCES
	//v1 will now be used for reading the source and v2 will be used to read in the "destination"
	while(1){
		fscanf(in, "%d", &v1);
		fscanf(in, "%d", &v2);
		if( (v1 == 0) && (v2 == 0) ){ // indicates end of file
			break;
		}
		
		List pathList = newList();
		BFS(myGraph, v1);
		
		if(getDist(myGraph, v2) == INF){
			fprintf(out, "The distance from %d to %d is infinity\n", v1, v2);
			fprintf(out, "No %d-%d path exists\n", v1, v2);
		}else{
			getPath(pathList, myGraph, v2);
			fprintf(out, "The distance from %d to %d is %d\n", v1, v2, (length(pathList)-1) );
			fprintf(out, "A shortest %d-%d path is: ", v1, v2);
			printList(out, pathList);
			fprintf(out, "\n\n");
		}
		freeList(&pathList);
	}
	
	freeGraph(&myGraph);
	fclose(in);
	fclose(out);
	return(0);
}
	
			
		
	
	
		
		
	