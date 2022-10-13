package homework6;

import java.util.Comparator;

/**
 * Sorts words by case insensitive alphabetical order, implements Comparator
 * interface.
 * Andrew ID: yuanyin
 *
 * @author EvaYin
 */
public class IgnoreCase implements Comparator<Word> {
    /**
     * Compare two Word objects based on based on insensitive alphabets, override
     * compare() in Comparator.
     *
     * @param o1 Word to be compared
     * @param o2 Word to be compared
     * @return int positive if o1 is larger, negative if o2 is larger
     */
    @Override
    public int compare(Word o1, Word o2) {
        return o1.getWord().compareToIgnoreCase(o2.getWord());
    }
}
