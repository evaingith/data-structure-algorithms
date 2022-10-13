package homework2;

import java.util.ArrayDeque;
import java.util.LinkedList;

/**
 * 17-683 Data Structures for Application Programmers. Homework Assignment 2
 * Solve Josephus problem using different data structures and different
 * algorithms and compare running times.
 * Out of the three methods, I would use the LinkedList as "List" to find
 * the survivor's position, especially when size and rotation are large.
 * Because the other two runs at O(n^2) and this LinkedList as "List" runs
 * at O(n). For the addLast() and removeFirst() of ArrayDeque as Queue/Deque
 * and LinkedList as Queue/Deque, they are all O(1), but ArrayDeque is
 * generally faster because no doubleCapacity() would happen and initializing
 * a new Node() and changing the pointers would take longer than just
 * assigning an element to the tail index. But it depends on the number of
 * size and rotation, for example, if size=10000, rotation=10, LinkedList as
 * "List" would be the slowest and ArrayDeque as Queue/Deque is preferred.
 * AndrewID:yuanyin
 * @author EvaYin
 */
public class Josephus {

    /**
     * Uses ArrayDeque class as Queue/Deque to find the survivor's position.
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater
     *                 than 0
     * @return The position value of the survivor
     */
    public int playWithAD(int size, int rotation) {
        int position = 0;
        if (size <= 0 || rotation <= 0) {
            throw new RuntimeException();
        }
        if (size == 1 || rotation == 1) {
            return size;
        }
        ArrayDeque<Integer> ad = new ArrayDeque<>(size);
        for (int i = 1; i <= size; i++) {
            ad.addLast(i);
        }
        int adLength = ad.size();
        while (adLength != 0) {
            for (int j = 0; j < rotation - 1; j++) {
                ad.addLast(ad.removeFirst());
            }
            position = ad.removeFirst();
            adLength--;
        }
        return position;
    }

    /**
     * Uses LinkedList class as Queue/Deque to find the survivor's position.
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater
     *                 than 0
     * @return The position value of the survivor
     */
    public int playWithLL(int size, int rotation) {
        int position = 0;
        if (size <= 0 || rotation <= 0) {
            throw new RuntimeException();
        }
        if (size == 1 || rotation == 1) {
            return size;
        }
        LinkedList<Integer> ll = new LinkedList<>();
        for (int i = 1; i <= size; i++) {
            ll.addLast(i);
        }
        int llLength = ll.size();
        while (llLength != 0) {
            for (int j = 0; j < rotation - 1; j++) {
                ll.addLast(ll.removeFirst());
            }
            position = ll.removeFirst();
            llLength--;
        }
        return position;
    }

    /**
     * Uses LinkedList class to find the survivor's position.
     *
     * However, do NOT use the LinkedList as Queue/Deque Instead, use the LinkedList
     * as "List" That means, it uses index value to find and remove a person to be
     * executed in the circle
     *
     * Note: Think carefully about this method!! When in doubt, please visit one of
     * the office hours!!
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater
     *                 than 0
     * @return The position value of the survivor
     */
    public int playWithLLAt(int size, int rotation) {
        int position = 0;
        if (size <= 0 || rotation <= 0) {
            throw new RuntimeException();
        }
        if (size == 1 || rotation == 1) {
            return size;
        }
        LinkedList<Integer> llAt = new LinkedList<>();
        for (int i = 1; i <= size; i++) {
            llAt.add(i);
        }
        int llLength = llAt.size();
        int eliminated = (rotation - 1) % llLength;
        while (llLength > 1) {
            llAt.remove(eliminated);
            llLength--;
            eliminated = (eliminated + rotation - 1) % llLength;
        }
        position = llAt.get(0);
        return position;
    }
}
