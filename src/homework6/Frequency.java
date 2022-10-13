package homework6;

import java.util.Comparator;

/**
 * Sorts words according to their frequencies (a word with highest frequency
 * comes first), implements Comparator interface.
 * Andrew ID: yuanyin
 *
 * @author EvaYin
 */
public class Frequency implements Comparator<Word> {
    /**
     * Compare two Word objects based on their frequencies in descending order,
     * override compare() in Comparator.
     *
     * @param o1 Word to be compared
     * @param o2 Word to be compared
     * @return int positive if o1 is larger, negative if o2 is larger
     */
    @Override
    public int compare(Word o1, Word o2) {
        return Integer.compare(o2.getFrequency(), o1.getFrequency());
    }
}
