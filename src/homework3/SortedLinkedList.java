package homework3;

/**
 * 17683 Data Structures for Applications Programmers.
 * Homework 3 SortedLinkedList Implementation with Recursion.
 * @author EvaYin
 */
public class SortedLinkedList implements MyListInterface {
    /**
     * Head node of the SortedLinkedList.
     */
    private Node head;

    private static class Node {
        /**
         * Each node has a string data.
         */
        private String data;
        /**
         * Each node point to the next node or null.
         */
        private Node next;
        Node(String s, Node n) {
            this.data = s;
            this.next = n;
        }
    }
    /**
     * Constructor without parameter for SortedLinkedList.
     */
    public SortedLinkedList() {
        head = null;
    }
    /**
     * Constructor with parameter of an unsorted array for SortedLinkedList.
     * @param unsorted String array to be used for constructing SortedLinkedList
     */
    public SortedLinkedList(String[] unsorted) {
        createSortedLinkedList(unsorted, 0);
    }
    /**
     * Using add() to construct the SortedLinkedList recursively.
     * @param input String array to be used for constructing SortedLinkedList
     * @param index integer for indexing the String array
     */
    private void createSortedLinkedList(String[] input, int index) {
        if (input != null && input.length != 0 && index < input.length) {
            add(input[index]);
            createSortedLinkedList(input, index + 1);
        }
    }
    /**
     * If input value is invalid or duplicated, ignore;
     * after adding, SortedLinkedList is in ascending order;
     * if SortedLinkedList is empty, create head node with String value;
     * if String value should be before head, it becomes the new head.
     * @param value String to be added
     */
    @Override
    public void add(String value) {
        if (value != null && !value.isEmpty()) {
            if (isEmpty()) {
                head = new Node(value, null);
            } else if (value.compareTo(head.data) < 0) {
                Node added = new Node(value, head);
                head = added;
            } else if (!contains(value)) {
                addRec(head, value);
            }
        }
    }
    /**
     * Base case: traverse to the last node, add at the end of last node
     * or find a place in the middle where String data should be added,
     * insert it; else, recurse to the next node.
     * @param data String to be added
     * @param node node to be recursed
     */
    private void addRec(Node node, String data) {
        if (node.next == null) {
            node.next = new Node(data, null);
        } else if (data.compareTo(node.data) > 0 && data.compareTo(node.next.data) < 0) {
            Node added = new Node(data, null);
            added.next = node.next;
            node.next = added;
        } else {
            addRec(node.next, data);
        }
    }
    /**
     * Checks the size (number of data items) of the list.
     * @return the size of the list
     */
    @Override
    public int size() {
        return sizeRec(head);
    }
    /**
     * Base case: input node is null or recurse to the end of last node,
     * return 0; else, add 1 to every recursive level.
     * @param node node to be recursed
     * @return the size of the list
     */
    private int sizeRec(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + sizeRec(node.next);
    }
    /**
     * Displays the values of the list,
     * if list is empty, print "[]"; else, create a StringBuilder
     * to store the values, print the strings between "[" and "]".
     */
    @Override
    public void display() {
        if (isEmpty()) {
            System.out.println("[]");
        } else {
            Node curr = head;
            StringBuilder sb = new StringBuilder();
            System.out.println("[" + displayRec(curr, sb) + "]");
        }
    }
    /**
     * Base case: input node.next is null or recurse to the end of last node,
     * return StringBuilder as string; else, append ", " and recurse; append
     * curr.data every time get to a new recursive level.
     * @param node node to be recursed
     * @param sb StringBuilder to append strings
     * @return String object with the content of SortedLinkedList in ascending order
     */
    private String displayRec(Node node, StringBuilder sb) {
        sb.append(node.data);
        if (node.next == null) {
            return sb.toString();
        } else {
            sb.append(", ");
            return displayRec(node.next, sb);
        }
    }
    /**
     * Returns true if the key value is in the list,
     * if list is empty, return false.
     * @param key String key to search
     * @return true if found, false if not found
     */
    @Override
    public boolean contains(String key) {
        if (isEmpty()) {
            return false;
        }
        return containsRec(head, key);
    }
    /**
     * Base case: find the node with String key, return it, or
     * recurse to the last node still did not find it, return false;
     * else, recurse to the next level with next node.
     * @param key String key to search
     * @param node node to be recursed
     * @return true if found, false if not found
     */
    private boolean containsRec(Node node, String key) {
        if (node.data.equals(key)) {
            return true;
        }
        if (node.next == null) {
            return false;
        } else {
            return containsRec(node.next, key);
        }
    }
    /**
     * Returns true is the list is empty.
     * @return true if it is empty, false if it is not empty
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }
    /**
     * Removes and returns the first String object of the list.
     * @return String object that is removed. If the list is empty, returns null
     */
    @Override
    public String removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node temp = head;
        head = head.next;
        return temp.data;
    }
    /**
     * Removes and returns String object at the specified index,
     * if remove first node, call removeFirst().
     * @param index index integer to remove String object
     * @return String object that is removed
     * @throws RuntimeException for invalid index value (index < 0 || index >= size())
     */
    @Override
    public String removeAt(int index) {
        if (index < 0 || index >= size()) {
            throw new RuntimeException("invalid index value");
        } else if (index == 0) {
            return removeFirst();
        } else {
            return removeAtRec(head, index);
        }
    }
    /**
     * Base case: find the node before the node intended to be removed by index,
     * remove the proper node, change the pointer from it to the next node,
     * and return data of removed node; else, recurse to the next level with
     * the next level.
     * @param index index to remove String object
     * @param node node to be recursed
     * @return String object that is removed
     */
    private String removeAtRec(Node node, int index) {
        if (index == 1) {
            Node temp = node.next;
            node.next = node.next.next;
            return temp.data;
        }
        return removeAtRec(node.next, index--);
    }
}
