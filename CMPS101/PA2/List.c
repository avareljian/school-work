/****************************************************************************************
*  List.c
*  Implementation file for List ADT
*  Alex Vareljian CMPS101 pa2
*  avarelji
*****************************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include "List.h"

/* structs ******************************************************************************/

/* private NodeObj type*/
typedef struct NodeObj{
	int value;
	struct NodeObj* next;
	struct NodeObj* prev;
} NodeObj;

/* private Node type*/
typedef NodeObj* Node;

/* private ListObj type*/
typedef struct ListObj{
	Node head;				// pointer to the start of the list
	Node tail;				// pointer to the end of the list
	Node cursor;			// pointer to the "current" element. not necessaraily defined.
	int index;				// index of the "current" element (0-length-1). = -1 if undefined.
	int length;				// number of elements in the list
} ListObj;


/* constructors and deconstructors ******************************************************/

// newNode()
// Returns reference to new Node object. Initializes 'value', 'next', and 'prev'.
// Private
Node newNode(int x){
	Node N = malloc(sizeof(NodeObj));
	N->value = x;
	N->next = NULL;
	N->prev = NULL;
	return N;
}

// freeNode()
// frees heap memory pointed to by *pN and sets *pN to NULL
// Private
void freeNode(Node* pN){
	if( pN != NULL && *pN != NULL ){
		free(*pN);
		*pN = NULL;
	}
}

// newList()
// Returns a reference to a new empty list
List newList(void){
	List L;
	L = malloc(sizeof(ListObj));
	L->head = L->tail = L->cursor = NULL;
	L->index = -1;
	L->length = 0;
	return L;
}

// freeList()
// frees all heap memory associated with List *pL and sets *pL to NULL
void freeList(List* pL){
	if( pL != NULL && *pL != NULL ){
		if( !isEmpty(*pL) ){
			clear(*pL);
		}
		free(*pL);
		*pL = NULL;
	}
}

/* access functions ********************************************************************/

// returns length of list
int length(List L){
	if( L == NULL ){
		printf("List error: calling length() on NULL List reference\n");
		exit(1);
	}
	return( L->length );
}

// returns index of list
// if index is undefined, return -1
int index(List L){
	if( L == NULL ){
		printf("List error: calling index() on NULL List reference\n");
		exit(1);
	}
	if( !validIndex(L) ){
		return -1;
	}
	return( L->index );
}

// returns head element of List
// Pre: !isEmpty
int front(List L){
	if( L == NULL ){
		printf("List error: calling front() on NULL List reference\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling front() empty list\n");
		exit(1);
	}
	return(L->head->value);
}

// returns tail element of List
// pre: !isEmpty
int back(List L){
	if( L == NULL ){
		printf("List error: calling back() on NULL List reference\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling back() empty list\n");
		exit(1);
	}
	return(L->tail->value);
}
	
// returns the data of the current element
// pre: cursor must be defined	
int get(List L){
	if( L == NULL ){
		printf("List error: calling get() on NULL List reference\n");
		exit(1);
	}
	if( !validIndex(L) ){
		printf("List error: calling get() on undefined cursor\n");
		exit(1);
	}
	return(L->cursor->value);
}

// returns true (1) if A and B are the same list, false (0) otherwise	
int equals(List A, List B){
	if( A == NULL || B == NULL ){
		printf("List error: calling get() on NULL List reference\n");
		exit(1);
	}
	if( (A->length) == ((B->length) == 0 )){
		return 1;
	}
	
	Node currentA = NULL;
	Node currentB = NULL;
	if( A->length == B->length ){
		currentA = A->head;
		currentB = B->head;
		
		while( currentA != NULL && currentB != NULL ){
			//printf("In while loop\n");
			if( currentA->value != currentB->value ){
				return 0;
			}
			currentA = currentA->next;
			currentB = currentB->next;
		}
		return 1;
	}else{
		return 0;
	}	
}

// isEmpty()
// returns true (1) if L is empty, otherwise returns false(0)
int isEmpty(List L){
	if( L == NULL ){
		printf("List error: calling isEmpty() on NULL List reference\n");
		exit(1);
	}
	return(L->length==0);
}

// validIndex()
// returns true(1) if L has a defined cursor/index, false (0) otherwise
// pre: !isEmpty
int validIndex(List L){
	if( L == NULL ){
		printf("List error: calling validIndex() on NULL List reference\n");
		exit(1);
	}
	else if( L->index < 0 ){
		return 0;
	}
	else if( L->index > L->length){
		return 0;
	}else{
		return 1;
	}
}


/* manipulation procedures *************************************************************/

// clear()
// deletes (and frees) all Nodes in the List
// resets list to an empty state
void clear(List L){
	if( L == NULL ){
		printf("List error: calling clear() on NULL List reference\n");
		exit(1);
	}
	/*if( length(L) == 0 ){
		printf("List error: calling clear() on an empty list\n");
		exit(1);
	}*/
	while( !isEmpty(L) ){
		deleteFront(L);
	}
	freeNode(&L->cursor);
	freeNode(&L->head);
	freeNode(&L->tail);
	L->length = 0;
	L->index = -1;
}

// moveFront()
// moves cursor to head of the list
// pre: !isEmpty
void moveFront(List L){
	if( L == NULL ){
		printf("List error: calling moveFront() on NULL List reference\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling moveFront() on an empty list\n");
		exit(1);
	}
	L->cursor = L->head;
	L->index = 0;
}

// moveBack()
// moves cursor to tail of the list
// pre: !isEmpty
void moveBack(List L){
	if( L == NULL ){
		printf("List error: calling moveFront() on NULL List reference\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling moveFront() on an empty list\n");
		exit(1);
	}
	L->cursor = L->tail;
	L->index = length(L)-1;
}

// movePrev()
// moves cursor one position closer to head. if cursor is at head, cursor becomes undefined.
// if cursor undefined, does nothing.
// pre: !isEmpty
void movePrev(List L){
	if( L == NULL ){
		printf("List error: calling movePrev() on NULL List reference\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling movePrev() on an empty list\n");
		exit(1);
	}
	if( index(L) > 0 && index(L) <= length(L)-1 ){
		L->cursor = L->cursor->prev;
		L->index--;
		return;
	}else{
		L->index = -1;
	}
	return;
}

// moveNext()
// moves cursor one position closer to tail. if cursor is at tail, cursor becomes undefined.
// if cursor is undefined, does nothing.
// pre: !isEmpty
void moveNext(List L){
	if( L == NULL ){
		printf("List error: calling moveNext() on NULL List reference\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling moveNext() on an empty list\n");
		exit(1);
	}
	if( index(L) >= 0 && index(L) < length(L)-1 ){
		L->cursor = L->cursor->next;
		L->index++;
	}else{
		L->index = -1;
	}
	return;
}

// prepend()
// creates new Node and prepends it to the list
void prepend(List L, int data){
	if( L == NULL ){
		printf("List error: calling prepend() on NULL List reference\n");
		exit(1);
	}
	Node latest = newNode(data);
	if( length(L) == 0 ){ // if list was empty
		L->head = latest;
		L->tail = L->head;
		
		if( validIndex(L) ){
		L->index++;
		}
		L->length++;
		
	}else{
		L->head->prev = latest;
		latest->next = L->head;
		L->head = latest;
		
		if( validIndex(L) ){
		L->index++;
		}
		L->length++;
	}
}

// append()
// creates new Node and appends it to the list
void append(List L, int data){
	if( L == NULL ){
		printf("List error: calling apppend() on NULL List reference\n");
		exit(1);
	}
	Node latest = newNode(data);
	if( length(L) == 0 ){ // if list was empty
		L->head = latest;
		L->tail = L->head;	
	}else{
		latest->prev = L->tail;
		L->tail->next = latest;
		L->tail = latest;
	}	
	L->length++;
}

// insertBefore()
// creates new Node and inserts it before the cursor
// pre: validIndex and !isEmpty
void insertBefore(List L, int data){
	if( L == NULL ){
		printf("List error: calling insertBefore() on NULL List reference\n");
		exit(1);
	}
	if( !validIndex(L) ){
		printf("List error: calling insertBefore() on a list with undefined cursor\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling insertBefore() on an empty list\n");
		exit(1);
	}
	if( index(L) == 0 ){ // if cursor is at head
		prepend(L, data);
		return;
	}else{
		Node latest = newNode(data);
		
		Node temp = L->cursor->prev;
		latest->prev = temp;
		latest->next = L->cursor;
		
		temp->next = latest;
		L->cursor->prev = latest;
		
		L->index++;
		L->length++;
		

	}
}

// insertAfter()
// creates a new Node and inserts it after the cursor
// pre: validIndex and !isEmpty
void insertAfter(List L, int data){
	if( L == NULL ){
		printf("List error: calling insertAfter() on NULL List reference\n");
		exit(1);
	}
	if( !validIndex(L) ){
		printf("List error: calling insertAfter() on a list with undefined cursor\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling insertAfter() on an empty list\n");
		exit(1);
	}
	if( index(L) == length(L)-1 ){ // if cursor is at tail
		append(L, data);
		return;
	}else{
		Node latest = newNode(data);
		
		Node temp = L->cursor->next;
		latest->prev = L->cursor;
		latest->next = temp;
		
		temp->prev = latest;
		L->cursor->next = latest;
		
		L->length++;
		

	}
}

// deleteFront()
// deletes the first element of the list
// pre: !isEmpty
void deleteFront(List L){
	if( L == NULL ){
		printf("List error: calling deleteFront() on NULL List reference\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling deleteFront() on an empty list\n");
		exit(1);
	}
	Node N = L->head;
	if( length(L) == 1 ){
		L->head = L->tail = NULL;
	}else{
		L->head = L->head->next;
	}
	L->length--;
	if( index(L) == 0 ){
		L->index = -1;
	}else{
		L->index--;
	}
	
	freeNode(&N);
}

// deleteBack()
// deletes the last element of the list
// pre: !isEmpty
void deleteBack(List L){
	if( L == NULL ){
		printf("List error: calling deleteBack() on NULL List reference\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling deleteBack() on an empty list\n");
		exit(1);
	}
	Node N = L->tail;
	if( length(L) == 1 ){
		L->head = L->tail = NULL;
	}else{
		L->tail = L->tail->prev;
		L->tail->next = NULL;
		
		if( index(L) == length(L)-1 ){
		L->cursor = NULL;
		L->index = -1;
	}
		
	}
	L->length--;
	freeNode(&N);		
}

// delete()
// deletes the cursor element of the list. cursor becomes undefined.
// pre: !isEmpty and validIndex
void delete(List L){
	if( L == NULL ){
		printf("List error: calling delete() on NULL List reference\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling delete() on an empty list\n");
		exit(1);
	}
	if( !validIndex(L) ){
		printf("List error: calling delete() while cursor is undefined\n");
		exit(1);
	}
	// if cursor is at head
	if( index(L) == 0 ){
		L->head = L->head->next;
		L->head->prev = NULL;
		
		freeNode(&L->cursor);
		L->cursor = NULL;
		L->index = -1;
		L->length--;
		return;
	}
	// if cursor is at tail
	if( index(L) == length(L)-1 ){
		L->tail = L->tail->prev;
		
		freeNode(&L->cursor);
		L->index = -1;
		L->length--;
		return;
	}
	if( length(L) == 1 ){
		L->head = L->tail = NULL;
		
		freeNode(&L->cursor);
		L->index = -1;
		L->length = 0;
		return;
	}
	
	L->cursor->prev->next = L->cursor->next;
	L->cursor->next = L->cursor->prev;
	
	freeNode(&L->cursor);
	L->cursor = NULL;
	L->index = -1;
	L->length--;
	

}

/* other operations ********************************************************************/

// plays roughly the same role as a toString function in java
void printList(FILE* out, List L){
	if( L == NULL ){
		printf("List error: calling printList() on NULL List reference\n");
		exit(1);
	}
	if( isEmpty(L) ){
		printf("List error: calling printList() on an empty list\n");
		exit(1);
	}
	Node current = L->head;
	while( current != NULL ){
		fprintf(out, "%d ", current->value);
		current = current->next;
	}
}

// returns a list that is a copy of the input
List copyList(List L){
	List B = newList();
	Node current = L->head;
	
	while( current != NULL ){
		//printf("in while of copyList\n");
		append(B, current->value );
		//printf("appended %d\n", current->value);
		//printf("Copied list is now: ");
		//printList(stdout, B);
		//printf("\n");
		current = current->next;
	}
	return B;
}

