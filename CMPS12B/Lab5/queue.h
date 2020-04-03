//---------------------------------------------------------------------------------------
// queue.h
// header file for Queue Lab5
// avarelji
// Alex Vareljian
//---------------------------------------------------------------------------------------

#ifndef _INTEGER_QUEUE_H_INCLUDE_
#define _INTEGER_QUEUE_H_INCLUDE_

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

// Queue
// Exported reference type
typedef struct QueueObj* Queue;

// Node
// Exported reference type 
typedef struct NodeObj* Node;
	
// newQueue()
// constructor for the Queue type
Queue newQueue(void);

// newNode()
// constructor for node
Node newNode(int x);

// freeQueue()
// destructor for the Queue type
void freeQueue(Queue* pR);

// freeNode()
// deconstructor for the Node type
void freeNode(Node* pN);

// enqueue()
// add a number to the end of the queue and return that node
// pre: none
Node enqueue(int number, Queue R);

// dequeue()
// delete number from the beginning of the queue and return that node
// pre: none
int dequeue(Queue R);

// printQueue()
// prints a text representation of R to the file pointed to by out
// pre: none
void printQueue(FILE* out, Queue R);

// isEmpty()
// returns 0 if false, 1 if true
// (0 if is not empty, 1 if is empty)
int isEmpty(Queue R);

#endif