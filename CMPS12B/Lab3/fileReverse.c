//---------------------------------------------------------------------------------------
// fileReverse.c
// avarelji 1539102
// takes two command line arguments, an in file an out file. prints each string from the
// input file to the output file on a seperate line and reversed
//---------------------------------------------------------------------------------------

#include <stdio.h>
#include<stdlib.h>
#include<string.h>

//swaps first and last characters, moving inward
void stringReverse(char* s){
	int i = 0;
	int z = strlen(s)-1;
	char temp;
	for (i = 0; i<z; i++, z--){
		temp = s[i];
		s[i] = s[z];
		s[z] = temp;
	}
}

int main(int argc, char* argv[]){
	FILE* in;
	FILE* out;
	char word[256]; //a char array to store words from the input file
	
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
	
	//read from in, reverse each string, print on seperate lines to out
	while(fscanf(in, " %s", word) != EOF){
		stringReverse(word);
		//printf(word);
		fprintf(out, "%s\n", word);
	}
	
	//close in and out files
	fclose(in);
	fclose(out);
	
	return(EXIT_SUCCESS);
}