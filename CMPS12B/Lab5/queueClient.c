//---------------------------------------------------------------------------------------
// queueClient.c
// Client of Queue. Contains main function. Takes two command line argurments, reads 
// lines of instructions from input file and prints to output file.
// avarelj
// Alex Vareljian
//---------------------------------------------------------------------------------------
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include "queue.h"

int main(int argc, char* argv[]){
	FILE* in;
	FILE* out;
	char line[256]; //a char array to store lines from the input file
	Queue qT = newQueue();

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
	
	while(fgets(line, sizeof(line), in)){ //while file in has next line
		if(*line == 'e'){
			int val;
			val = atoi(line+2);
			enqueue(val, qT);
			fprintf(out, "enqueued %d\n", val);
		}
		if(*line == 'd'){
			if(isEmpty(qT) == 1){
				fprintf(out, "empty\n");
			}else{
			fprintf(out, "%d\n", dequeue(qT));
			}
		}
		if(*line == 'p'){
			printQueue(out, qT);
		}
	}
	freeQueue(&qT);
	
	//close in and out files
	fclose(in);
	fclose(out);
	return(EXIT_SUCCESS);
}
			
		
		
		
		
		
		
		
		