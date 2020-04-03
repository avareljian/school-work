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
#define INF -2
#define NIL -3

/******************** Struct ********************/
typedef struct GraphObj{
	List *adjacent;
	int order;
	int size;
	int source;
	
	int *color;
	int *distance;
	int *parent;
} GraphObj;

/******************** Constructors-Destructors ********************/
Graph newGraph(int n){
	Graph G = malloc( sizeof(struct GraphObj) );
	G->adjacent = calloc( n+1, sizeof(List) );
	G->order = n;
	G->size = 0;
	G->source = NIL;
	
	G->color = calloc( n+1, sizeof(int) );
	G->distance = calloc( n+1, sizeof(int) );
	G->parent = calloc( n+1, sizeof(int) );
	
	for( int i = 1; i <= n; i++ ){
		G->adjacent[i] = newList();
		G->color[i] = WHITE;
		G->distance[i] = INF;
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
	free(G->distance);
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

int getSource(Graph G){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling getSource() on NULL Graph reference\n");
        exit(1);
    }
	return G->source;
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

int getDist(Graph G, int u){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling getDist() on NULL Graph reference\n");
        exit(1);
    }
	if( (u < 1) || (u > getOrder(G)) ) {
        fprintf(stderr, "Graph error: calling getDist() of a verex out of the bounds 1<= u <= Order\n");
        exit(1);
    }
	return G->distance[u];
}

// appends to list L the vertices of a shortest in G from source to u,
// or appends NIL to L if no such path exists
void getPath(List L, Graph G, int u){
	//printf("int u is %d.\n", u);
	
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling getPath() on NULL Graph reference\n");
        exit(1);
    }
	if( getSource(G) == NIL) {
        fprintf(stderr, "Graph error: calling getPath() BEFORE BFS was called\n");
        exit(1);
    }
	if( (u < 1) || (u > getOrder(G)) ) {
        fprintf(stderr, "Graph error: calling getPath() of a verex out of the bounds 1<= u <= Order\n");
        exit(1);
    }
	
	if(G->distance[u] == INF ){
		clear(L);
		return;
	}
	
	if( u == getSource(G) ){
		append(L, u);
		return;
	}
	if( G->parent[u] == NIL ){
		//append(L, NIL);
		return;
	}else{
		getPath(L, G, G->parent[u]);
		append(L, u);
	}	
}

/*// helper function for getPath
// does a path from G->source to u exist?
int doesPathExist(Graph G, int u){
	
	if( length(G->adjacent[u]) == 0 ){
		return -1;
	}
}
*/


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

// Runs BFS algorithm on graph G with source s, 
// setting color, distance, parent, and source fields of G accordingly
void BFS(Graph G, int s){
	if(G == NULL) {
        fprintf(stderr, "Graph error: calling BFS() on NULL Graph reference\n");
        exit(1);
    }
	
	for( int i = 0; i<= getOrder(G); i++ ){
		G->color[i] = WHITE;
		G->distance[i] = INF;
		G->parent[i] = NIL;
	}
	G->color[s] = GREY;
	G->distance[s] = 0;
	G->parent[s] = NIL;
	G->source = s;
	
	List Q = newList();
	append(Q, s);
	while( !isEmpty(Q) ){
		int x = front(Q);
		deleteFront(Q);
		
		List adjacentTo = G->adjacent[x];
		if( length(adjacentTo) > 0 ){
			for(moveFront(adjacentTo); index(adjacentTo) >= 0; moveNext(adjacentTo)){
				int y = get(adjacentTo);
				if( G->color[y] == WHITE ){
					G->color[y] = GREY;
					G->distance[y] = G->distance[x]+1;
					G->parent[y] = x;
					append(Q, y);
				}
			}
		}
		G->color[x] = BLACK;
	}
	freeList(&Q);
}


/******************** Other operations ********************/
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

	

