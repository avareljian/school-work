/****************************************************************************************
* Graph.c
* Implementation file for Graph ADT
* Alex Vareljian
* avarelji
****************************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include "Graph.h"

#define WHITE -1
#define GREY 0
#define BLACK 1
#define UNDEF -2
#define NIL 0

/******************** Struct ********************/
typedef struct GraphObj{
	List *adjacent;
	int order;
	int size;
	int time;
	
	int *color;
	int *discover;
	int *finish;
	int *parent;
} GraphObj;

/******************** Constructors-Destructors ********************/
Graph newGraph(int n){
	Graph G = malloc( sizeof(struct GraphObj) );
	G->adjacent = calloc( n+1, sizeof(List) );
	G->order = n;
	G->size = 0;
	G->time = 0;

	
	G->color = calloc( n+1, sizeof(int) );
	G->discover = calloc( n+1, sizeof(int) );
	G->finish = calloc( n+1, sizeof(int) );
	G->parent = calloc( n+1, sizeof(int) );
	
	for( int i = 1; i <= n; i++ ){
		G->adjacent[i] = newList();
		G->color[i] = WHITE;
		G->discover[i] = UNDEF;
		G->finish[i] = UNDEF;
		G->parent[i] = NIL;
	}
	return G;
}


void freeGraph(Graph* pG){
	Graph G = *pG;
	for( int i = 1; i <= getOrder(G); i++ ){
		freeList( &(G->adjacent[i]) );
	}
	free(G->adjacent);
	free(G->color);
	free(G->discover);
	free(G->finish);
	free(G->parent);
	free(*pG);
	*pG = NULL;
}


/******************** Access functions ********************/
int getOrder(Graph G){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling getOrder() on NULL Graph reference\n");
        exit(1);
    }
	return G->order;
}

int getSize(Graph G){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling getSize() on NULL Graph reference\n");
        exit(1);
    }
	return G->size;
}

int getDiscover(Graph G, int u){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling getDiscover() on NULL Graph reference\n");
        exit(1);
    }
	if( (u < 1) || (u > getOrder(G)) ) {
        fprintf(stderr, "Graph error: calling getDiscover() of a verex out of the bounds 1<= u <= Order\n");
        exit(1);
    }
	return G->discover[u];
}

int getFinish(Graph G, int u){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling getFinish() on NULL Graph reference\n");
        exit(1);
    }
	if( (u < 1) || (u > getOrder(G)) ) {
        fprintf(stderr, "Graph error: calling getFinish() of a verex out of the bounds 1<= u <= Order\n");
        exit(1);
    }
	return G->finish[u];
}

int getParent(Graph G, int u){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling getParent() on NULL Graph reference\n");
        exit(1);
    }
	if( (u < 1) || (u > getOrder(G)) ) {
        fprintf(stderr, "Graph error: calling getParent() of a verex out of the bounds 1<= u <= Order\n");
        exit(1);
    }
	return G->parent[u];
}


/******************** Manipulation procedures ********************/
// deletes all edges of G, restoring it to its original (no edge) state. i.e. a null graph.
void makeNull(Graph G){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling makeNull() on NULL Graph reference\n");
        exit(1);
    }
	for( int i = 1; i<= getOrder(G); i++ ){
		clear( G->adjacent[i] );
	}
	G->size = 0;
}

// inserts a new edge joining u to v. i.e. u is added to List v and v is added to List u.
// maintain these lists sorted by increasing lables
// pre: 1<= u, v <=getOrder(G)
void addEdge(Graph G, int u, int v){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling addEdge() on NULL Graph reference\n");
        exit(1);
    }
	if( (u < 1) || (u > getOrder(G)) ) {
        fprintf(stderr, "Graph error: calling addEdge(u,v) on a verex out of the bounds 1<= u <= Order\n");
        exit(1);
    }
	if( (v < 1) || (v > getOrder(G)) ) {
        fprintf(stderr, "Graph error: calling addEdge(u,v) on a verex out of the bounds 1<= v <= Order\n");
        exit(1);
    }
	
	// Add v to List u
	List L = G->adjacent[u];
	if( length(L) == 0 ){
		append(L, v);
	}else{
		for( moveFront(L); index(L) >= 0; moveNext(L) ){
			if( v < get(L) ){
				insertBefore(L, v);
				break;
			}
		}
		if( index(L) == -1 ){
			append(L, v);
		}
	}
	
	// Add u to List v
	L = G->adjacent[v];
	if( length(L) == 0 ){
		append(L, u);
	}else{
		for( moveFront(L); index(L) >= 0; moveNext(L) ){
			if( u < get(L) ){
				insertBefore(L, u);
				break;
			}
		}
		if( index(L) == -1 ){
			append(L, u);
		}
	}
	G->size++;
}

// inserts a directed edge from u to v. i.e. v is added to List u.
// pre: 1<= u, v <=getOrder(G)
void addArc(Graph G, int u, int v){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling addArc() on NULL Graph reference\n");
        exit(1);
    }
	if( (u < 1) || (u > getOrder(G)) ) {
        fprintf(stderr, "Graph error: calling addArc(u,v) on a verex out of the bounds 1<= u <= Order\n");
        exit(1);
    }
	if( (v < 1) || (v > getOrder(G)) ) {
        fprintf(stderr, "Graph error: calling addArc(u,v) on a verex out of the bounds 1<= v <= Order\n");
        exit(1);
    }
	// Add v to List u
	List L = G->adjacent[u];
	if( length(L) == 0 ){
		append(L, v);
	}else{
		for( moveFront(L); index(L) >= 0; moveNext(L) ){
			if( v < get(L) ){
				insertBefore(L, v);
				break;
			}
		}
		if( index(L) == -1 ){
			append(L, v);
		}
	}
	G->size++;
}

// part of the DFS algorithm
void visit(Graph G, List S, int x){
	if(G == NULL){
        fprintf(stderr, "Graph error: calling visit() on NULL Graph reference\n");
        exit(1);
    }
	
	//fprintf(stdout, "IN visit\n");

	
	int y = UNDEF;
	List adjacentTo = G->adjacent[x];
	G->color[x] = GREY;
	G->time++;
	G->discover[x] = G->time;
	
	if( length(adjacentTo) > 0 ){
	
		for( moveFront(adjacentTo); index(adjacentTo) >= 0; moveNext(adjacentTo) ){
			y = get(adjacentTo);
		
			if( G->color[y] == WHITE ){
				G->parent[y] = x;
				visit(G, S, y);
			}
		}
	}
	
	G->color[x] = BLACK;
	G->time++;
	G->finish[x] = G->time;
	//fprintf(stdout, "Finished %d. S is now: ", x);
	
	if( index(S) == -1 ){
		prepend(S, x);
	}else{
		insertAfter(S, x);	
	}
	
	//printList(stdout, S);
	//fprintf(stdout, "\n");
}

// pre: length(S) == getOrder(G)
void DFS(Graph G, List S){
	if(G == NULL){
        fprintf(stderr, "Graph error: calling DFS() on NULL Graph reference\n");
        exit(1);
    }
	if( length(S) != getOrder(G) ){
        fprintf(stderr, "Graph error: precondition for DFS has not been met. i.e. length(S) != getOrder(G)\n");
        exit(1);
    }
	//fprintf(stdout, "IN DFS\n");
	
	// reset all the vertices to undiscovered to make sure the algorithm works if you call it on the same graph twice
	for( int i = 1; i <= getOrder(G); i++){
		G->color[i] = WHITE;
	}
	// reset time to 0 for the same reason
	G->time = 0;
	
	
	int x = UNDEF;
	moveBack(S);
	while( index(S) >= 0 ){
		x = front(S);
		deleteFront(S);
		
		if( G->color[x] == WHITE ){
			visit(G, S, x);
		}
	}
}

/******************** Other operations ********************/
// returns a new graph that is the transpose of input graph
Graph transpose(Graph G){
	if(G == NULL){
        fprintf(stderr, "Graph error: calling transpose() on NULL Graph reference\n");
        exit(1);
    }
	
	int x;
	Graph Gt = newGraph(G->order);
	
	for( int i = 1; i<= getOrder(G); i++ ){
		List current = G->adjacent[i];
		
		if( length(current) == 0 ){
			continue;
		}else{
			for( moveFront(current); index(current) >= 0; moveNext(current) ){
				x = get(current);
				addArc(Gt, x, i);
			}
		}
	}
	return Gt;			
}

// returns a new graph which is copy of input graph
Graph copyGraph(Graph G){
	if(G == NULL){
        fprintf(stderr, "Graph error: calling copyGraph() on NULL Graph reference\n");
        exit(1);
    }
	int x;
	Graph N = newGraph(G->order);
	
	for( int i = 1; i<= getOrder(G); i++ ){
		List current = G->adjacent[i];
		
		if( length(current) == 0 ){
			continue;
		}else{
			for( moveFront(current); index(current) >= 0; moveNext(current) ){
				x = get(current);
				addArc(N, i, x);
			}
		}
	}
	return N;
}

// prints adjacency list representation of G to the file pointed to by out
void printGraph(FILE* out, Graph G){
	if(G == NULL){
        fprintf(stderr, "Graph error: calling printGraph() on NULL Graph reference\n");
        exit(1);
    }
	for( int i = 1; i <= getOrder(G); i++){
		List tmp = G->adjacent[i];
		fprintf(out, "%d: ", i);
		printList(out, tmp);
		fprintf(out, "\n");
	}
}

	

