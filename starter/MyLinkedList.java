/*
 * Name: Tristan Cooper
 * Email: tmcooper@ucsd.edu
 * PID: A17085814
 * Sources used: The notes I took on Week 3 Slides and Section 3.1 from ZyBooks
 * 
 * This file, MyLinkedList.java contains a generic class containing the 
 * attributes of a Double Linked List, as well as a nested class that contains
 * the attributes of a Node in the Double Linked List. 
 */

import java.util.AbstractList;


// Imports for PA4 ListIterator:
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;


/**
 * This generic class inherits from the AbstractList class that is a part of the 
 * Java Collections Framework. This means that some of the methods in 
 * MyLinkedList must use the @override keyword.
 * This class contains methods allowing a user to interact with the list, 
 * adding and removing elements, returning information about the list, etc.
 * 
 * Instance variables: (these won't come up until future PAs)
 * size - An int representing the number of valid elements in the list, not 
 * including the sentinel nodes.
 * head - A Node representing the sentinel head of the list.
 * tail - A Node representing the sentinel tail of the list.
 */
public class MyLinkedList<E> extends AbstractList<E> {

    int size; // Keep track of the number of nodes in the LL
    Node head; // Reference to the sentinel head of LL
    Node tail; // Reference to the sentinel tail of LL


    // NESTED CLASS
    /**
     * A Node class that holds data and references to previous and next Nodes.
     */
    protected class Node {
        E data;
        Node next;
        Node prev;

        /** 
         * Constructor to create singleton Node 
         * @param element Element to add, can be null	
         */
        public Node(E element) {
            // Initialize the instance variables
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /** 
         * Set the parameter prev as the previous node
         * @param prev new previous node
         */
        public void setPrev(Node prev) {
            this.prev = prev;		
        }

        /** 
         * Set the parameter next as the next node
         * @param next new next node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /** 
         * Set the parameter element as the node's data
         * @param element new element 
         */
        public void setElement(E element) {
            this.data = element;
        }

        /** 
         * Accessor to get the next Node in the list 
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }
        /** 
         * Accessor to get the prev Node in the list
         * @return the previous node  
         */
        public Node getPrev() {
            return this.prev;
        } 
        /** 
         * Accessor to get the Nodes Element 
         * @return this node's data
         */
        public E getElement() {
            return this.data;
        } 
    }

    /** 
     * No-arg constructor that creates an empty list by setting size to zero,
     * then initializing the head and tail sentinel nodes to null and making 
     * them point to each other.
     */
    public MyLinkedList() {
        // Sentinel nodes don't count as part of the size.
        this.size = 0;

        // Head and tail are null in empty list.
        this.head = new Node(null);
        this.tail = new Node(null);

        // Head and tail to point to each other in empty list.
        head.setNext(tail);
        tail.setPrev(head);
    }

    /**
     * Returns the "size" of the list.
     * @return the number of non-sentinel nodes in the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the element (data stored in the node) at the specified index.
     * @param index the index of the element to return.
     * @return the element at the specified index.
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            // Typecasting return statements is likely not necessary, but used 
            // just in case.
            return (E)head.getNext().getElement();
        }

        // Reference node to move through list. 
        // NOTE: not creating a new node.
        Node currNode = head;

        // Looping through list to find the node at the index.
        for (int i = 0; i <= index; i++) {
            // Moving forward through list.
            currNode = currNode.getNext();
        }

        return (E)currNode.getElement();
    }

    /**
     * Adds a node at the specified index from zero to size.
     * @param index the specific index to add the node at.
     * @param data the data contained in the added node.
     */
    @Override
    public void add(int index, E data) {
        if (data == null) {
            throw new NullPointerException();
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node(data);

        // Checking for empty list.
        if (size == 0) {
            add(data);
        }

        // Checking for adding to the front of the list.
        else if (index == 0) {
            // The new node's next node becomes the head's old next node.
            newNode.setNext(head.getNext());

            // The head's next node becomes the new node.
            head.setNext(newNode);

            // The newNode points backwards to the head.
            newNode.setPrev(head);

            // New node's next node needs to point backwards to the new node.
            newNode.getNext().setPrev(newNode);

            size++;
        }

        // Checking for adding to the end of the list.
        else if (index == size) {
            // The new node's previous node becomes the tail's previous node.
            newNode.setPrev(tail.getPrev());

            // The tail's previous node becomes the new node.
            tail.setPrev(newNode);

            // Tail becomes the new node's next node.
            newNode.setNext(tail);

            // New node's previous node needs to point forward to the new node.
            newNode.getPrev().setNext(newNode);

            size++;
        }

        else {
            // Reference node to move through list. 
            // NOTE: not creating a new node.
            Node currNode = head;

            // Looping through list to find the node at the index.
            for (int i = 0; i < index; i++) {
                // Moving forward through list.
                currNode = currNode.getNext();

                // Setting previous is unnecessary, but included for potential 
                // rare edge cases.
                currNode.setPrev(currNode.getPrev());
            }

            // The new node's next node becomes the current node's next node.
            newNode.setNext(currNode.getNext());

            // The current node's next node becomes the new node.
            currNode.setNext(newNode);

            // Current node becomes the new node's previous node.
            newNode.setPrev(currNode);

            newNode.getPrev().setNext(newNode);
            
            // New node's next node needs to point backwards to the new node.
            newNode.getNext().setPrev(newNode);
            size++;
        }
        // Not incrementing size at fallthrough here because calling add(data) 
        // for empty list increments size on it's own.
    }

    /**
     * Adds a node at the end of the list.
     * @return will always return true.
     */
    public boolean add(E data) {
        if (data == null) {
            throw new NullPointerException();
        }
        // Creating the new node to store the data.
        Node newNode = new Node(data);

        // Checking for empty list.
        if (size == 0) {
            // Head points forward to new node instead of tail.
            head.setNext(newNode);

            // New node points backwards to head.
            newNode.setPrev(head);

            // New node points forward to tail.
            newNode.setNext(tail);

            // Tail points backwards to new node instead of head.
            tail.setPrev(newNode);
        }

        // Non empty
        else {
            // The new node's previous node becomes the tail's previous node.
            newNode.setPrev(tail.getPrev());

            // The tail's previous node becomes the new node.
            tail.setPrev(newNode);

            // The new node's next node becomes the tail.
            newNode.setNext(tail);

            // New node's previous node needs to point forward to the new node.
            newNode.getPrev().setNext(newNode);
        }
        size++;
        return true; 
    }

    /**
     * Sets the data stored in the node at the specified index to the data in 
     * the argument and returns the data that was previously stored in that node
     * @param index the index of the node to set the data of.
     * @param data the data to set the node at the specified index to.
     * @return the data that was previously stored that node.
     */
    public E set(int index, E data) {
        if (data == null) {
            throw new NullPointerException();
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            // Storing the old data to return.
            E previousData = (E)head.getNext().getElement();

            // Overwriting the previous data with the new data.
            head.getNext().setElement(data);

            return previousData;
        }

        if (index == size - 1) {
            // Storing the old data to return.
            E previousData = (E)tail.getPrev().getElement();

            // Overwriting the previous data with the new data.
            tail.getPrev().setElement(data);

            return previousData;
        }

        Node currNode = head;
        for (int i = 0; i <= index; i++) {
            currNode = currNode.getNext();
        }
        // Storing the old data to return.
        E previousData = (E)currNode.getElement();

        // Overwriting the previous data with the new data.
        currNode.setElement(data);

        return previousData;
    }

    /**
     * Removes the node at a specific index and returns the data of the node it
     * just removed.
     * @param index the index of the node to remove.
     * @return the data of the node that was removed.
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        // Removing the first element in the list.
        if (index == 0) {
            // Storing the data of first element to be returned later
            E removedData = (E)head.getNext().getElement();

            // Pointing the head past the first element.
            head.setNext(head.getNext().getNext());

            // Pointing the new first element backwards to the head.
            head.getNext().setPrev(head);

            size --;
            return removedData;
        }

        // Removing the last element in the list.
        else if (index == size - 1) {
            // Storing the data of last element to be returned later.
            E removedData = (E)tail.getPrev().getElement();

            // Pointing the tail one node behind the last element.
            tail.setPrev(tail.getPrev().getPrev());

            // Pointing the new last element forward to the tail.
            tail.getPrev().setNext(tail);
            size--;
            return removedData;
        }

        else {
            Node currNode = head;
            for (int i = 0; i <= index; i++) {
                currNode = currNode.getNext();
            }

            E removedData = (E)currNode.getElement();

            // Skipping over the node to be removed by setting by pointing the 
            // node be fore it forward to the node after it.
            currNode.getPrev().setNext(currNode.getNext());

            // Same as above but backwards.
            currNode.getNext().setPrev(currNode.getPrev());

            // The data in the removed node set to null for testing and garbage
            // collection. 
            currNode.data = null;
            currNode.setNext(null);
            currNode.setPrev(null);

            size--;
            return removedData; 
        }
    }

    /*
     * Removes all nodes from the list except the head and tail (sentinels).
     */
    public void clear() {
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }

    /**
     * Determines if the list is empty.
     * @return true if the list is empty, false if it's not.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the node at the specified index.
     * @param index the index of the node to return.
     * @return the node at the specified index.
     */
    protected Node getNth(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            return (Node) head.getNext();
        }

        Node currNode = head;
        for (int i = 0; i < index; i++) {
            currNode = currNode.getNext();
        }

        return (Node) currNode.getNext();
    }


    protected class MyListIterator implements ListIterator<E> {

        // Class variables
        Node left;
        Node right;
        int idx;

        // True after calling next(). 
        // False after calling previous().
        boolean forward; 

        // True after next() or previous(). 
        // False after add() or remove().
        boolean canRemoveOrSet; 


        /**
         * Constructor for the MyListIterator class used to initialize the 
         * iterator.
         * EXCEPTIONS: None.
         */
        public MyListIterator() {
            left = head;
            right = head.getNext();
            idx = 0;
            forward = false;
            canRemoveOrSet = false;
        }

        /**
         * Returns true if there is an element node when going in the forward 
         * (head to tail) direction from the current iterator position.
         * Sentinel (dummy) nodes do not count as element nodes.
         * EXCEPTIONS: None.
         * @return true if there is an element node when moving forward. 
         *         false, otherwise.
         */
        public boolean hasNext() {
            // There is a next node (not at the end of the list)
            if (right != tail) {
                return true;
            }
            return false;
        }

        /**
         * Return the next element in the list when going forward, and move the 
         * iterator forward by one node.
         * EXCEPTIONS: NoSuchElementException if there is no next element.
         * @return the next element in the list.
         */
        public E next() {
            if (hasNext()) {
                left = right;
                right = right.getNext();

                canRemoveOrSet = true;
                forward = true;

                idx++;

                return (E) left.getElement();
            }
            // If there isn't a next element, fallthrough to the exception.
            throw new NoSuchElementException();
        }

        /**
         * Returns true if there is an element node when going in the backward 
         * (tail to head) direction from the current iterator position.
         * Sentinel (dummy) nodes do not count as element nodes.
         * EXCEPTIONS: None.
         * @return true if there is an element node when moving backward. 
         *         false, otherwise.
         */
        public boolean hasPrevious() {
            if (left != head) {
                return true;
            }
            return false;
        }

        /**
         * Return the previous element in the list when going backward, and move 
         * the iterator backward by one node.
         * EXCEPTIONS: NoSuchElementException if there is no previous element.
         * @return the previous element in the list.
         */
        public E previous() {
            if (hasPrevious()) {
                right = left;
                left = left.getPrev();

                canRemoveOrSet = true;
                forward = false;

                idx--;

                return (E) right.getElement();
            }
            // If there isn't a previous element, fallthrough to the exception.
            throw new NoSuchElementException();
        }

        /**
         * Returns the index of the element that would be returned by a call to 
         * next(). Returns the list size if at the end of the list.
         * EXCEPTIONS: None.
         * @return see above
         */
        public int nextIndex() {
            return idx;
        }

        /**
         * Returns the index of the element that would be returned by a call to 
         * previous(). Returns -1 if at the start of the list.
         * EXCEPTIONS: None.
         * @return see above
         */
        public int previousIndex() {
            return idx - 1;
        }

        /**
         * Insert the given item into the list immediately before the element 
         * that would be return by next().
         * If we call previous() immediately following add(), the newly added 
         * item would be returned.
         * The value of the current index of the list iterator is increased by 
         * one.
         * EXCEPTIONS: NullPointerException if element is null.
         */
        public void add(E element) {
            if (element == null) {
                throw new NullPointerException();
            }

            Node newNode = new Node(element, left, right);

            left.setNext(newNode);
            right.setPrev(newNode);

            left = newNode;
            canRemoveOrSet = false;
            size++;
        }

        /**
         * For the node returned by the most recent next()/previous() call,
         * replace its value with the new value element.
         * EXCEPTIONS: - NullPointerException if element is null.
         *             - IllegalStateException if neither next() nor previous()
         *                  were called, or if add() or remove() have been
         *                  called since the most recent next()/previous() call.
         * @param element the new value for the node being set.
         */
        public void set(E element) {
            if (element == null) {
                throw new NullPointerException();
            }
            if (!canRemoveOrSet) {
                throw new IllegalStateException();
            }

            // If moving forward, the most recent node called is to the left.
            if (forward) {
                left.setElement(element);
            }
            // If moving backward, the most recent node called is to the right.
            else {
                right.setElement(element);
            }
        }

        /**
         * Remove the last element node returned by the most recent next() or
         * previous() call.
         * EXCEPTIONS: IllegalStateException, same conditions as set().
         */
        public void remove() {
            if (!canRemoveOrSet) {
                throw new IllegalStateException();
            }

            // If moving forward, remove the node to the left
            if (forward) {
                left.getPrev().setNext(right);
                right.setPrev(left.getPrev());
                left = left.getPrev();
            }

            // If moving backward, remove node to the right
            else if (!forward) {
                right.getNext().setPrev(left);
                left.setNext(right.getNext());
                right = right.getNext();
            }
        }

    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }
    @Override
    public Iterator<E> iterator() {
        return new MyListIterator();
    }
}
