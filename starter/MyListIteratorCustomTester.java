/*
 * Name: Tristan Cooper
 * Email: tmcooper@ucsd.edu
 * PID: A17085814
 * Sources used: Referenced MyListIteratorPublicTester.java starter code for 
 *                 tester structure/formatting.
 * 
 * This file, MyListIteratorCustomTester.java, contains "hidden" tests for PA4's
 * MyListIterator.
*/

import static org.junit.Assert.*;
import org.junit.*;
import java.util.NoSuchElementException; // Custom import

/**
 * This class contains "hidden"" test cases for MyListIterator. 
 * It borrows heavily from MyListIteratorPublicTester.java
 * 
 * Instance variables:
 * listLen0 - a linkedlist of length 0
 * listLen1 - a linkedlist of length 1
 * listLen2 - a linkedlist of length 2
 */
public class MyListIteratorCustomTester {

     /** Instance variables */
    private MyLinkedList listLen0, listLen1, listLen2;
    private MyLinkedList.MyListIterator listLen0Iterator, listLen1Iterator, 
                listLen2Iterator;

    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test.
     */
    @Before
    public void setUp() throws Exception {

        listLen0 = new MyLinkedList();
        listLen0Iterator = listLen0.new MyListIterator();

        listLen1 = new MyLinkedList();
        listLen1.add("Only element");
        listLen1Iterator = listLen1.new MyListIterator();

        listLen2 = new MyLinkedList();
        listLen2.add("First element");
        listLen2.add("Second element");
        listLen2Iterator = listLen2.new MyListIterator();
    }

    /**
     * Aims to test the next() method when iterator is at end of the list 
     */
    @Test (expected = NoSuchElementException.class)
    public void testNextEnd() {
        // No next element in empty list.
        listLen0Iterator.next();

        listLen1Iterator.next();
        listLen1Iterator.next();

        listLen2Iterator.next();
        listLen2Iterator.next();
        listLen2Iterator.next();

        assertFalse("Unable to remove node", listLen0Iterator.canRemoveOrSet);
        assertFalse("Unable to remove node", listLen1Iterator.canRemoveOrSet);
        assertFalse("Unable to remove node", listLen2Iterator.canRemoveOrSet);

        assertFalse("call hasNext when there isn't a next node",
                listLen0Iterator.hasNext());
        assertFalse("call hasNext when there isn't a next node",
                listLen1Iterator.hasNext());
        assertFalse("call hasNext when there isn't a next node",
                listLen2Iterator.hasNext());

        assertTrue("Iterator should still be moving forward",listLen0Iterator.forward);
        assertTrue("Iterator should still be moving forward",listLen1Iterator.forward);
        assertTrue("Iterator should still be moving forward",listLen2Iterator.forward);

        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen1Iterator.left, listLen1.head.getNext());
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen2Iterator.left, listLen2.head.getNext().getNext());
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen2Iterator.left, listLen2.tail.getPrev());
        assertNull("Element to the right of iterator should be null",
                listLen0Iterator.right);
        assertNull("Element to the right of iterator should be null",
                listLen1Iterator.right);
        assertNull("Element to the right of iterator should be null",
                listLen2Iterator.right);
    }

    /**
     * Aims to test the previous() method when iterator is at the start of the 
     * list 
     */
    @Test (expected = NoSuchElementException.class)
    public void testPreviousStart() {
        listLen0Iterator.previous();
        listLen1Iterator.previous();
        listLen2Iterator.previous();

        assertFalse("Can't remove at start", listLen0Iterator.canRemoveOrSet);
        assertFalse("Can't remove at start", listLen1Iterator.canRemoveOrSet);
        assertFalse("Can't remove at start", listLen2Iterator.canRemoveOrSet);

        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen0Iterator.right, listLen0.head.getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen0Iterator.idx, 0);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen1Iterator.right, listLen1.head.getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen1Iterator.idx, 0);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen2Iterator.right, listLen2.head.getNext().getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen2Iterator.idx, 2);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen2Iterator.right, listLen2.tail.getPrev());

    }

    /**
     * Aims to test the add(E e) method when an invalid element is added
     */
    @Test (expected = NullPointerException.class)
    public void testAddInvalid() {
        listLen0Iterator.add(null);
        listLen1Iterator.add(null);
        listLen2Iterator.add(null);

        // Manually doing listLen1Iterator.next();
        listLen1Iterator.left = listLen2.head.getNext();
        listLen1Iterator.right = listLen2.head.getNext().getNext();
        listLen1Iterator.idx = 1;
        listLen1Iterator.canRemoveOrSet = true;
        listLen1Iterator.forward = true;

        // Testing that add should throw an exception no matter where the 
        // iterator is in the list.
        listLen1Iterator.add(null);

        // Manually doing listLen2Iterator.next();
        listLen2Iterator.left = listLen2.head.getNext();
        listLen2Iterator.right = listLen2.head.getNext().getNext();
        listLen2Iterator.idx = 1;
        listLen2Iterator.canRemoveOrSet = true;
        listLen2Iterator.forward = true;

        // Testing that add should throw an exception no matter where the
        // iterator is in the list.
        listLen2Iterator.add(null);

        // Test that size hasn't changed.
        assertEquals("add(null) shouldn't change size", 0, listLen0.size());
        assertEquals("add(null) shouldn't change size", 1, listLen1.size());
        assertEquals("add(null) shouldn't change size", 2, listLen2.size());
    }

    /**
     * Aims to test the set(E e) method when canRemoveOrSet is false
     */
    @Test (expected = IllegalStateException.class)
    public void testCantSet() {
        listLen0Iterator.set("canRemoveOrSet is false");
        listLen1Iterator.set("canRemoveOrSet is false");
        listLen2Iterator.set("canRemoveOrSet is false");

        // Test that size hasn't changed.
        assertEquals("set() shouldn't change size", 0, listLen0.size());
        assertEquals("set() shouldn't change size", 1, listLen1.size());
        assertEquals("set() shouldn't change size", 2, listLen2.size());

        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen0Iterator.right, listLen0.head.getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen0Iterator.idx, 0);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen1Iterator.right, listLen1.head.getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen1Iterator.idx, 0);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen2Iterator.right, listLen2.head.getNext().getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen2Iterator.idx, 2);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen2Iterator.right, listLen2.tail.getPrev());
    }

    /**
     * Aims to test the set(E e) method when an invalid element is set
     */
    @Test (expected = NullPointerException.class)
    public void testSetInvalid() {
        // Manually doing listLen1Iterator.next();
        listLen1Iterator.left = listLen1.head.getNext();
        listLen1Iterator.right = listLen1.head.getNext().getNext();
        listLen1Iterator.idx = 1;
        listLen1Iterator.canRemoveOrSet = true;
        listLen1Iterator.forward = true;

        listLen1Iterator.set(null);

        // Manually doing listLen2Iterator.next().next();
        listLen2Iterator.left = listLen2.head.getNext().getNext();
        listLen2Iterator.right = listLen2.head.getNext().getNext().getNext();
        listLen2Iterator.idx = 2;
        listLen2Iterator.canRemoveOrSet = true;
        listLen2Iterator.forward = true;
        
        listLen2Iterator.set(null);
   
        // Test that size hasn't changed.
        assertEquals("set() shouldn't change size", 0, listLen0.size());
        assertEquals("set() shouldn't change size", 1, listLen1.size());
        assertEquals("set() shouldn't change size", 2, listLen2.size());

        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen0Iterator.right, listLen0.head.getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen0Iterator.idx, 0);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen1Iterator.right, listLen1.head.getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen1Iterator.idx, 0);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen2Iterator.right, listLen2.head.getNext().getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen2Iterator.idx, 2);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen2Iterator.right, listLen2.tail.getPrev());
    }

    /**
     * Aims to test the remove() method when canRemoveOrSet is false
     */
    @Test (expected = IllegalStateException.class)
    public void testCantRemove() {
        listLen0Iterator.remove();
        listLen1Iterator.remove();
        listLen2Iterator.remove();

        // Test that size hasn't changed.
        assertEquals("remove() shouldn't change size", 0, listLen0.size());
        assertEquals("remove() shouldn't change size", 1, listLen1.size());
        assertEquals("remove() shouldn't change size", 2, listLen2.size());

        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen0Iterator.right, listLen0.head.getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen0Iterator.idx, 0);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen1Iterator.right, listLen1.head.getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen1Iterator.idx, 0);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen2Iterator.right, listLen2.head.getNext().getNext());
        assertEquals("index shouldn't have changed with invalid call",
                listLen2Iterator.idx, 2);
        assertEquals("Iterator position shouldn't have changed with invalid call",
                listLen2Iterator.right, listLen2.tail.getPrev());
    }

    /**
     * Aims to tests the hasNext() method at the end of a list
     */
    @Test
    public void testHasNextEnd() {
        // An empty list is technically already at the end of the list when 
        // Iterator is initialized.
        assertFalse("call hasNext when there is NOT a next node", 
                listLen0Iterator.hasNext());

        // Manually doing listLen1Iterator.next();
        listLen1Iterator.left = listLen1.head.getNext();
        listLen1Iterator.right = listLen1.head.getNext().getNext();
        listLen1Iterator.idx = 1;
        listLen1Iterator.canRemoveOrSet = true;
        listLen1Iterator.forward = true;
        assertFalse("call hasNext when there is NOT a next node", 
                listLen1Iterator.hasNext());

        // Manually doing listLen2Iterator.next().next();
        listLen2Iterator.left = listLen2.head.getNext().getNext();
        listLen2Iterator.right = listLen2.head.getNext().getNext().getNext();
        listLen2Iterator.idx = 2;
        listLen2Iterator.canRemoveOrSet = true;
        listLen2Iterator.forward = true;
        assertFalse("call hasNext when there is NOT a next node", 
                listLen2Iterator.hasNext());
    }

    /**
     * Aims to test the hasPrevious() method at the start of a list
     */
    @Test
    public void testHasPreviousStart() {
        assertFalse("call hasPrevious when there is NOT a previous node", 
                listLen0Iterator.hasPrevious());
        assertFalse("call hasPrevious when there is NOT a previous node", 
                listLen1Iterator.hasPrevious());
        assertFalse("call hasPrevious when there is NOT a previous node", 
                listLen2Iterator.hasPrevious());
    }

    /**
     * Aims to test the previousIndex() method at the start of a list
     */
    @Test
    public void testPreviousIndexStart() {
        // Each should return -1.
        assertEquals("call previousIndex when iterator is at beginning of list", 
                -1, listLen0Iterator.previousIndex());
        assertEquals("call previousIndex when iterator is at beginning of list", 
                -1, listLen1Iterator.previousIndex());
        assertEquals("call previousIndex when iterator is at beginning of list", 
                -1, listLen2Iterator.previousIndex());
    }

    /**
     * Aims to test the nextIndex() method at the end of a list
     */
    @Test
    public void testNextIndexEnd() {
        // Each should return the size of the list.
        assertEquals("call nextIndex when iterator is at end of list", 
                0, listLen0Iterator.nextIndex());

        // Manually doing listLen1Iterator.next();
        listLen1Iterator.left = listLen1.head.getNext();
        listLen1Iterator.right = listLen1.head.getNext().getNext();
        listLen1Iterator.idx = 1;
        listLen1Iterator.canRemoveOrSet = true;
        listLen1Iterator.forward = true;
        assertEquals("call nextIndex when iterator is at end of list", 
                1, listLen1Iterator.nextIndex());

        // Manually doing listLen2Iterator.next().next();
        listLen2Iterator.left = listLen2.head.getNext().getNext();
        listLen2Iterator.right = listLen2.head.getNext().getNext().getNext();
        listLen2Iterator.idx = 2;
        listLen2Iterator.canRemoveOrSet = true;
        listLen2Iterator.forward = true;
        assertEquals("call nextIndex when iterator is at end of list", 
                2, listLen2Iterator.nextIndex());
    }
}
