/****************************************************************************************
*  List.h
*  Header file for List ADT
*  Alex Vareljian CMPS101 pa2
*  avarelji
*****************************************************************************************/

#ifndef _List_H_INCLUDE_
#define _List_H_INCLUDE_

/* exported type ***********************************************************************/
typedef struct ListObj* List;

/* constructors and deconstructors *****************************************************/

// newList()
// returns referent to new empty list object
List newList(void);

// freeList()
// frees all heap memory associated with List *pL and sets *pL to NULL
void freeList(List* pL);

//* access functions *******************************************************************/

// returns length of list
int length(List L);

// returns index of list
// if index is undefined, return -1
int index(List L);

// returns head element of List
// Pre: !isEmpty
int front(List L);

// returns tail element of List
// pre: !isEmpty
int back(List L);

// returns the data of the current element
// pre: cursor must be defined	
int get(List L);

// returns true (1) if A and B are the same list, false (0) otherwise	
int equals(List A, List B);

// isEmpty()
// returns true (1) if L is empty, otherwise returns false(0)
int isEmpty(List L);

// validIndex()
// returns true(1) if L has a defined cursor/index, false (0) otherwise
// pre: !isEmpty
int validIndex(List L);

/* manipulation procedures *************************************************************/

// clear()
// deletes (and frees) all Nodes in the List
// resets list to an empty state
void clear(List L);

// moveFront()
// moves cursor to head of the list
// pre: !isEmpty
void moveFront(List L);

// moveBack()
// moves cursor to tail of the list
// pre: !isEmpty
void moveBack(List L);

// movePrev()
// moves cursor one position closer to head. if cursor is at head, cursor becomes undefined.
// if cursor undefined, does nothing.
// pre: !isEmpty
void movePrev(List L);

// moveNext()
// moves cursor one position closer to tail. if cursor is at tail, cursor becomes undefined.
// if cursor is undefined, does nothing.
// pre: !isEmpty
void moveNext(List L);

// prepend()
// creates new Node and prepends it to the list
void prepend(List L, int data);

// append()
// creates new Node and appends it to the list
void append(List L, int data);

// insertBefore()
// creates new Node and inserts it before the cursor
// pre: validIndex and !isEmpty
void insertBefore(List L, int data);

// insertAfter()
// creates a new Node and inserts it after the cursor
// pre: validIndex and !isEmpty
void insertAfter(List L, int data);

// deleteFront()
// deletes the first element of the list
// pre: !isEmpty
void deleteFront(List L);

// deleteBack()
// deletes the last element of the list
// pre: !isEmpty
void deleteBack(List L);

// delete()
// deletes the cursor element of the list. cursor becomes undefined.
// pre: !isEmpty and validIndex
void delete(List L);

/* other operations ********************************************************************/

// plays roughly the same role as a toString function in java
void printList(FILE* out, List L);

// returns a list that is a copy of the input
List copyList(List L);

#endif
