/****************************************************************************************
*  Lex.c
*  Client of List ADT. Uses it to "sort" an array via an insertion sort algorithm.
*  Alex Vareljian CMPS101 pa2
*  avarelji
*****************************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"List.h"


int main(int argc, char* argv[]){
	FILE* in;
	FILE* out;
	char line[256]; // a char array to store lines from the input file
	char* file[256]; // array of pointers to strings
	List myList = newList();
	int numberLines = 0;
	int x = 0;
	
	printf("Checkpoint %d\n", 1);
	
	//check command line for correct number of arguments
	if(argc !=3){
		printf("Usage: %s <input file> <output file>\n", argv[0]);
		exit(EXIT_FAILURE);
	}
		
	printf("Checkpoint %d\n", 2);

	
	//open in file for reading
	in = fopen(argv[1], "r");
	if(in == NULL){
		printf("Unable to read from file %s\n", argv[1]);
		exit(EXIT_FAILURE);
	}
		
	printf("Checkpoint %d\n", 3);

	//open output file for writing
	out = fopen(argv[2], "w");
	if(out == NULL){
		printf("Unable to write to file %s\n", argv[2]);
		exit(EXIT_FAILURE);
	}
		
	printf("Checkpoint %d\n", 4);

	while(fgets(line, 256, in) != NULL){ //while file in has next line
		numberLines++;
	}
		
	printf("Checkpoint %d\n", 5);
	printf("numberLines is %d\n", numberLines);
	
	// go back to the beginning of the file
	rewind(in);
	printf("Checkpoint %d\n", 6);

	
	// allocate memory
	for( int i = 0; i<= numberLines; i++ ){
		//printf("In for loop lmao. i = %d\n", i);
		file[i] = calloc(1, sizeof(char* [256]));		
	}

		
	printf("Checkpoint %d\n", 7);

	// read the file again and copy it to the array
	while(fgets(line, 256, in) != NULL ){
		printf("In while");
		strcpy(file[x], line );
		x++;
	}
		
	printf("Checkpoint %d\n", 8);

	// perform the 'sorting' using the list adt
	
	// append the first index
	append(myList, 0);
		
	printf("Checkpoint %d\n", 9);

	// iterate through the lines of the file
	for( int i = 1; i<= numberLines; i++ ){
		
		printf("Checkpoint for loop. Iterate through the lines of the file. %d\n", 10);
		
		// if current line is smaller than the first, just prepend it
		if( strcmp(file[i], file[front(myList)]) < 0 ){
			prepend(myList, i);
			continue;
		}
		
		// if current line is greater than the last, just append it
		if( strcmp(file[i], file[back(myList)]) > 0 ){
			append(myList, i);
			continue;
		}
		
		// otherwise, iterate through the list and find the right place to put it
		for( moveFront(myList); index(myList)>=0; moveNext(myList) ){
			if(strcmp( file[i], file[get(myList)] ) < 0 ){
				insertBefore(myList, i);
				break;
			}
		}
	}
		
	printf("Checkpoint %d\n", 11);

	
	// write the array to the outfile
	for( moveFront(myList); index(myList)>=0; moveNext(myList) ){
		fprintf(out, "%s", file[get(myList)]);
	}
	
		
	printf("Checkpoint %d\n", 12);


	//close in and out files
	fclose(in);
	fclose(out);
	
	freeList(&myList);
	
	// free memory
	for(int i=0; i<=numberLines; i++){
		free( file[i] );
	}
	
	
	return(0);
}
	