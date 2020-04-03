//---------------------------------------------------------------------------------------
// queue.c
// implementation file for Queue Lab5
// avarelji
// Alex Varejian
//---------------------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include "queue.h"
#include <assert.h>

typedef struct QueueObj{
   Node head;
   Node tail;
} QueueObj;

typedef struct NodeObj{
   int value;
   struct NodeObj* next;
} NodeObj;

//constructor for Queue type
Queue newQueue(void){
	Queue R = malloc(sizeof(QueueObj));
	assert(R != NULL);
	R->head = NULL;
	R->tail = NULL;
	return R;
}

//constructor for Node type
Node newNode(int x){
	Node N = malloc(sizeof(NodeObj));
	assert(N != NULL);
	N->value = x;
	N->next = NULL;
	return N;
}

//deconstructor for Queue type
void freeQueue(Queue* pR){
	if(pR != NULL && *pR != NULL){
		//free all heap memory associated with *pR
		free(*pR);
		*pR = NULL;
	}
}

//deconstructor for Node type
void freeNode(Node* pN){
	if(pN != NULL && *pN != NULL){
		free(*pN);
		*pN = NULL;
	}
}

//add an int to the end of the list by first creating a Node containing that int, and return that node
Node enqueue(int number, Queue R){
	Node toNQ = newNode(number);
	if(R->head == NULL){          //if it's the first node to be added to the list,
		R->head = toNQ;           //set head to point to it
	}else if(R->tail == NULL){    //if it's the second element to be be added to the list.
		R->tail = toNQ;           //set tail to point to it
		R->head->next = R->tail;  //link head and tail. (When there are just two nodes in the list, the first is head and the second is tail!)
	}else{
		R->tail->next = toNQ;     //otherwise, set it to the end of the list,
		R->tail = toNQ;           //then set tail to point to it
	}
	return toNQ;
}

//delete the Node at the beginning of the queue and return that node
int dequeue(Queue R){
	Node toDQ = R->head;
	R->head = R->head->next;
	return toDQ->value;
}

//print the queue to the output file
void printQueue(FILE* out, Queue R){
	//Trying to print a queue that doesn't exist
	if(R == NULL){
		fprintf(stderr, "Queue Error: printQueue() called on NULL Queue reference\n");
		exit(EXIT_FAILURE);
	}
	//If the queue is empty, print a newline
	if(R->head == NULL){
		fprintf(out, "\n");
	}
	//Print from the beginning 
	Node current;
	current = R->head;
	while(current != NULL){
		if(current->next == NULL){
			fprintf(out, "%d\n", current->value);
		}else{
			fprintf(out, "%d ", current->value);
		}
	current = current->next;
	}
}

// isEmpty()
// returns 0 if false, 1 if true
// (0 if is not empty, 1 if is empty)
int isEmpty(Queue R){
	if( R->head == NULL){
		R->tail = NULL;
		return 1;
	}
	return 0;
}
	

