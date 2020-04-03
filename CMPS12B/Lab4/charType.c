// Takes two command line args, an input and output file. Reads from the input,
// classifying the characters on each LINE of the input file into the following
// categories: alphabet chars (upper or lower case), numeric chars (0-9), punctuation,
// and white space (space, tab, newline). Chars not of these categories will be ignored.
// Alex Vareljian
// avarelji

#include<stdio.h>
#include<ctype.h>
#include<stdlib.h>
#include<string.h>

// Takes in input string s, copy its chars into their appropriate char arrays,
// given the pointers to those arrays.
// the arrays terminate with null char '\0' making them valid C strings
void extract_chars(char* s, char* a, char* d, char* p, char* w){
	int aa = 0, da = 0, pa = 0, wa = 0, i = 0;
	
	//read the string and copy each char into their designated array
	//also happens to track of the amounts of each type of char
	while(*(s + i) != '\0'){
		if(isalpha((int) *(s + i))){
			a[aa] = *(s + i);
			aa++;
		}
		if(isdigit((int) *(s + i))){
			d[da] = *(s + i);
			da++;
		}
		if(ispunct((int) *(s + i))){
			p[pa] = *(s + i);
			pa++;
		}
		if(isspace((int) *(s + i))){
			w[wa] = *(s + i);
			wa++;
		}		
		i++;
	}
	//add null char to the end of each array, making them into a string
	a[aa] = '\0';
	d[da] = '\0';
	p[pa] = '\0';
	w[wa] = '\0';
}

int main(int argc, char* argv[]){
	FILE* in;
	FILE* out;
	char line[256]; //a char array to store lines from the input file
	int aa = 0, da = 0, pa = 0, wa = 0, i = 0;
	char *A, *D, *P, *W;
	int counter = 0;
	
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
		counter++; //increment counter of which line it's on
		i = 0;     //reset the counters of types of char
		aa = 0;    //and i at the beginning of reading each line
		da = 0;
		pa = 0;
		wa = 0;
		
		//count each type of char so you know how much memory to allocate
		while(*(line + i) != '\0'){
			if(isalpha((int) *(line + i))){
				aa ++;
			}
			if(isdigit((int) *(line + i))){
				da++;
			}
			if(ispunct((int) *(line + i))){
				pa++;
			}
			if(isspace((int) *(line + i))){
				wa++;
			}		
			i++;
		}
		A = malloc(aa*sizeof(char) +1); //Allocate enough memory for aa chars, +1 so \0 can be added at the end
		D = malloc(da*sizeof(char) +1);
		P = malloc(pa*sizeof(char) +1);
		W = malloc(wa*sizeof(char) +1);
		
		//Write the chars to arrays, adding \0 to the end to make them strings
		extract_chars(line, A, D, P, W);
		
		//Print the strings
		fprintf(out, "line %d contains:\n", counter);
		if(aa == 1){
			fprintf(out, "%d alphabetic character: ", aa);
			fprintf(out, "%s\n", A);
		}else{
			fprintf(out, "%d alphabetic characters: ", aa);
			fprintf(out, "%s\n", A);
		}
		if(da == 1){
			fprintf(out, "%d numeric character: ", da);
			fprintf(out, "%s\n", D);
		}else{
			fprintf(out, "%d numeric characters: ", da);
			fprintf(out, "%s\n", D);
		}
		if(pa == 1){
			fprintf(out, "%d punctuation character: ", pa);
			fprintf(out, "%s\n", P);
		}else{
			fprintf(out, "%d punctuation characters: ", pa);
			fprintf(out, "%s\n", P);
		}
		if(wa == 1){
			fprintf(out, "%d whitespace character: ", wa);
			fprintf(out, "%s\n", W);
		}else{
			fprintf(out, "%d whitespace characters: ", wa);
			fprintf(out, "%s\n", W);
		}
		
		//After reading the line, making its arrays, and printing the contents,
		//free the pointers and set them to null so they can be reused for the
		//next lines
		free(A);
		free(D);
		free(P);
		free(W);
		A = NULL;
		D = NULL;
		P = NULL;
		W = NULL;
	}
	//close in and out files
	fclose(in);
	fclose(out);
	return(EXIT_SUCCESS);
}