package homework1;
/**
 * 17-683 Data Structures for Application Programmers.
 * Homework 1 create my own array class called, MyArray
 *
 * AndrewID:yuanyin
 * @author EvaYin
 */
public class MyArray {
    /**
     * * Number of element in MyArray.
     */
    private int size;
    /**
     * Length of MyArray.
     */
    private int capacity;
    /**
     * String array that is the underlined data structure.
     */
    private String[] myArray;
    /**
     * Default myArray lengths.
     */
    private final int defaultLength = 10;
    public MyArray() {
        myArray = new String[defaultLength];
        this.capacity = defaultLength;
    }
    public MyArray(int initialCapacity) {
        myArray = new String[initialCapacity];
        this.capacity = initialCapacity;
    }
    /**
     * worst-case running time complexity: O(n).
     * when inserting, duplicates are allowed
     * add method should take care of validating words
     * array should double its length when necessary
     * add string to the end of my array, ignore null
     * @param text text to be added
     */
    public void add(String text) {
        if (text != null && text.length() != 0 && text.matches("[a-zA-Z]+")) {
            if (capacity == 0) {
                this.capacity++;
                myArray = new String[capacity];
            }
            if (size == capacity) {
                this.capacity = capacity * 2;
                String[] newArray = new String[capacity];
                System.arraycopy(myArray, 0, newArray, 0, size);
                myArray = newArray;
            }
            myArray[size] = text;
            size++;
        }
    }
    /**
     * worst-case running time complexity: O(n).
     * search word in myArray, return false if input is null or blank
     * @param key key to be searched for
     * @return boolean of whether myArray contains the key
     */
    public boolean search(String key) {
        if (key == null || key.length() == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (myArray[i].equals(key)) {
                return true;
            }
        }
        return false;
    }
    /**
     * worst-case running time complexity: O(1).
     * return size of myArray
     * @return integer of size of myArray
     */
    public int size() {
        return size;
    }
    /**
     * worst-case running time complexity: O(1).
     * return capacity of myArray
     * @return integer of capacity of myArray
     */
    public int getCapacity() {
        this.capacity = myArray.length;
        return capacity;
    }
    /**
     * worst-case running time complexity: O(n).
     * display the entire myArray, whitespace between each word
     */
    public void display() {
        for (String word : myArray) {
            if (word != null) {
                System.out.print(word + " ");
            }
        }
        System.out.println("");
    }
    /**
     * worst-case running time complexity: O(n^2).
     * define all the occurrence except the first of certain word as duplicates,
     * convert duplicates in myArray to null,
     * remove null by shifting elements to fill the position
     */
    public void removeDups() {
        int sizeBefore = size;
        for (int i = size - 1; i >= 0; i--) {
            if (indexOf(myArray[i]) != i) {
                myArray[i] = null;
                size--;
            }
        }
        for (int k = 0; k < sizeBefore - 1; k++) {
            if (myArray[k] == null) {
                for (int m = 0; m < sizeBefore - k; m++) {
                    myArray[m + k] = myArray[m + k + 1];
                }
            }
        }
    }
    /**
     * worst-case running time complexity: O(n).
     * get index of the first occurrence of desired element,
     * return -1 if string not in myArray
     * @param duplicateOrNot string
     * @return int index of the first occurrence of input string
     */
    private int indexOf(String duplicateOrNot) {
        for (int j = 0; j < getCapacity(); j++) {
            if (myArray[j].equalsIgnoreCase(duplicateOrNot)) {
                return j;
            }
        }
        return -1;
    }
}
