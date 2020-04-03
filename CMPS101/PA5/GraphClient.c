//-----------------------------------------------------------------------------
// GraphClient.c
// Test client for the Graph ADT
//-----------------------------------------------------------------------------
#include<stdio.h>
#include<stdlib.h>
#include"List.h"
#include"Graph.h"


int main(int argc, char* argv[]){
   int i, n=100;
   List L = newList();
   Graph A = newGraph(n);
   Graph T=NULL, C=NULL;

   for (__uint8_t i = 1; i <= 100; i++)
          if (getDiscover(A, i) != UNDEF) return 1;
        addEdge(A, 64, 4);
        addEdge(A, 64, 3);
        addEdge(A, 42, 2);
        addEdge(A, 2, 64);
        addEdge(A, 4, 2);
        addEdge(A, 3, 42);
		
		fprintf(stdout, "\n");
		printGraph(stdout, A);
		fprintf(stdout, "\n");
		
        for (__uint8_t i = 1; i <= 100; i++) {
          prepend(L, i);
        }
		
		fprintf(stdout, "\n");
		printList(stdout, L);
		fprintf(stdout, "\n");
		
        DFS(A, L);
		
		fprintf(stdout, "\n");
		printList(stdout, L);
		fprintf(stdout, "\n");
		
		fprintf(stdout, "\n");
		fprintf(stdout, "x:  d  f  p\n");
		for(i=1; i<=n; i++){
			fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(A, i), getFinish(A, i), getParent(A, i));
		}
		fprintf(stdout, "\n");
		
		
        if (getDiscover(A, 100) != 1) return 2;
        if (getDiscover(A, 64) != 73) return 3;
        if (getDiscover(A, 4) != 75) return 4;
        DFS(A, L);
		
		
		fprintf(stdout, "\n_____________________________________________________________________________________________\n");
		
		fprintf(stdout, "\n");
		printList(stdout, L);
		fprintf(stdout, "\n");
		
		fprintf(stdout, "\n");
		printList(stdout, L);
		fprintf(stdout, "\n");
		
		fprintf(stdout, "\n");
		fprintf(stdout, "x:  d  f  p\n");
		for(i=1; i<=n; i++){
			fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(A, i), getFinish(A, i), getParent(A, i));
		}
		fprintf(stdout, "\n");
		
		
        if (getDiscover(A, 4) != 121) return 5;
 


	freeList(&L);
   freeGraph(&A);
  // freeGraph(&T);
  // freeGraph(&C);
   return(0);
 
}
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
