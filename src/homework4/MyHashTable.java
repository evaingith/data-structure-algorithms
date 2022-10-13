package homework4;
/**
 * 17683 Data Structures for Application Programmers. Homework Assignment 4:
 * HashTable Implementation with linear probing.
 *
 * Andrew ID: yuanyin
 * @author EvaYin
 */
public class MyHashTable implements MyHTInterface {
    /**
     * static load factor of 0.5.
     */
    private static final double LOADFACTOR = 0.5;
    /**
     * static default capacity of 10.
     */
    private static final int DEFAULTCAPACITY = 10;
    /**
     * static number of total characters of 27.
     */
    private static final int TOTALCHARACTER = 27;
    /**
     * static deduction to get ASCII lower case char of 96.
     */
    private static final int CHARACTEROFFSET = 96;
    /**
     * size, number of element in array.
     */
    private int size;
    /**
     * length of array.
     */
    private int arrayLength;
    /**
     * number of collisions.
     */
    private int numOfCollisions = 0;
    /**
     * The DataItem array of the table.
     */
    private DataItem[] hashArray;
    /**
     * Default constructor with no argument.
     */
    public MyHashTable() {
        this(DEFAULTCAPACITY);
    }
    /**
     * Constructor with input integer of capacity.
     * @param capacity input integer of capacity of hashArray
     */
    public MyHashTable(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException("invalid input capacity");
        }
        hashArray = new DataItem[capacity];
        arrayLength = hashArray.length;
        size = 0;
    }
    /**
     * Instead of using String's hashCode, you are to implement your own here. You
     * need to take the table length into your account in this method.
     *
     * In other words, you are to combine the following two steps into one step. 1.
     * converting Object into integer value 2. compress into the table using modular
     * hashing (division method)
     *
     * Helper method to hash a string for English lowercase alphabet and blank, we
     * have 27 total. But, you can assume that blank will not be added into your
     * table. Refer to the instructions for the definition of words.
     *
     * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
     *
     * But, to make the hash process faster, Horner's method should be applied as
     * follows;
     *
     * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
     * (((var4*n + var3)*n + var2)*n + var1)*n + var0
     *
     * Note: You must use 27 for this homework.
     *
     * However, if you have time, I would encourage you to try with other constant
     * values than 27 and compare the results but it is not required.
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int hashFunc(String input) {
        return hashValue(input);
    }
    /**
     * Use linear probing to get the real hashedIndex the item belongs to.
     * @param input input string for which the real hash index needs to be calculated
     * @return int real hash index of the input string
     */
    private int realIndex(String input) {
        int hashedIndex = hashFunc(input);
        while (hashArray[hashedIndex] != null && hashArray[hashedIndex].frequency != 0
                && !hashArray[hashedIndex].value.equals(input)) {
            hashedIndex += 1;
            if (hashedIndex >= arrayLength) {
                hashedIndex = hashedIndex % arrayLength;
            }
        }
        for (int i = hashedIndex; i < hashArray.length; i++) {
            if (hashArray[i] != null && hashArray[i].frequency != 0 && hashArray[i].value.equals(input)) {
                return i;
            }
        }
        return hashedIndex;
    }

    /**
     * Doubles array length and rehash items whenever the load factor is reached.
     * Update size and numOfCollisions accordingly.
     */
    private void rehash() {
        int newArrayLength = arrayLength * 2;
        newArrayLength = nextPrime(newArrayLength);
        DataItem[] newHashArray = new DataItem[newArrayLength];
        arrayLength = newHashArray.length;
        numOfCollisions = 0;
        System.out.println("Rehashing " + size + " items, new length is " + arrayLength);
        for (DataItem item : hashArray) {
            if (item != null && item.frequency != 0) {
                int newIndex = hashFunc(item.value);
                if (newHashArray[newIndex] == null) {
                    newHashArray[newIndex] = item;
                } else {
                    while (newIndex < newHashArray.length && newHashArray[newIndex] != null) {
                        newIndex += 1;
                    }
                    numOfCollisions++;
                    newHashArray[newIndex] = item;
                }
            }
        }
        hashArray = newHashArray;
    }

    /**
     * Check if input number is prime, if not find the next prime larger than it.
     * @param curr integer to be checked if it is prime
     * @return the input integer if prime, if not, return the next prime larger
     * than it
     */
    private int nextPrime(int curr) {
        while (!isPrime(curr)) {
            curr += 1;
        }
        return curr;
    }

    /**
     * Check current number is prime.
     * @param input integer to be checked if it is prime
     * @return true if prime, false if not prime
     */
    private boolean isPrime(int input) {
        if (input <= 1) {
            return false;
        } else {
            for (int i = 2; i < input; i++) {
                if (input % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;

        DataItem(String v) {
            this(v, 1);
        }

        DataItem(String v, int f) {
            this.value = v;
            this.frequency = f;
        }
    }

    /**
     * Returns the size, number of items, of the table.
     * @return the number of items in the table
     */
    public int size() {
        return this.size;
    }

    /**
     * Inserts a new String value (word). Increase frequency if value exists. Rehash
     * if size/arrayLength larger than load factor. Update size and numOfCollisions
     * accordingly.
     * @param value String value to add
     */
    public void insert(String value) {
        if (value != null && value.length() != 0 && value.matches("[a-zA-Z]+")) {
            int hashCode = realIndex(value);
            if (hashArray[hashCode] == null || hashArray[hashCode].frequency == 0) {
                hashArray[hashCode] = new DataItem(value);
                size += 1;
                for (DataItem item : hashArray) {
                    if (item != null && item.frequency != 0 && !item.value.equals(value)
                            && hashFunc(item.value) == hashFunc(value)) {
                        numOfCollisions++;
                        break;
                    }
                }
            } else {
                hashArray[hashCode].frequency += 1;
            }
            if ((double) size / (double) arrayLength > LOADFACTOR) {
                rehash();
            }
        }
    }

    /**
     * Displays the values of the table as [Value, Frequency]. Shows ** for empty item and #DEL# for
     * deleted item.
     */
    public void display() {
        for (DataItem item : hashArray) {
            if (item == null) {
                System.out.print("**");
            } else if (item.frequency == 0) {
                System.out.print("#DEL#");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("[").append(item.value).append(", ").append(item.frequency).append("]");
                System.out.print(sb.toString());
            }
            System.out.print(" ");
        }
        System.out.println();
    }

    /**
     * Returns true if value is contained in the table.
     * @param key String key value to search
     * @return true if found, false if not found or invalid input.
     */
    public boolean contains(String key) {
        int index = realIndex(key);
        if (hashArray[index] != null && hashArray[index].frequency != 0 && hashArray[index].value.equals(key)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of collisions in relation to insert and rehash.
     * @return number of collisions
     */
    public int numOfCollisions() {
        return numOfCollisions;
    }

    /**
     * Returns the hash value of a String, wrap every time to solve integer
     * overflow. Assume that String value is going to be a word with all lower case
     * letters.
     * @param value value for which the hash value should be calculated
     * @return int hash value of a String
     */
    public int hashValue(String value) {
        int hashCode = 0;
        for (char c : value.toCharArray()) {
            hashCode = (hashCode * TOTALCHARACTER + c - CHARACTEROFFSET) % arrayLength;
        }
        return hashCode;
    }

    /**
     * Returns the frequency of a key String, return 0 if invalid input.
     * @param key string value to find its frequency
     * @return frequency value if found. If not found, return 0
     */
    public int showFrequency(String key) {
        int index = hashFunc(key);
        if (index >= 0) {
            index = realIndex(key);
            if (hashArray[index] != null && hashArray[index].frequency != 0) {
                return hashArray[index].frequency;
            }
        }
        return 0;
    }

    /**
     * Removes and returns removed value, key.
     * @param key String to remove
     * @return value that is removed. If not found, return null
     */
    public String remove(String key) {
        int index = hashValue(key);
        for (int i = index; i < arrayLength; i++) {
            if (hashArray[i] != null && hashArray[i].frequency != 0 && hashArray[i].value.equals(key)) {
                size -= 1;
                hashArray[i].frequency = 0;
                return hashArray[i].value;
            }
        }
        return null;
    }
}
