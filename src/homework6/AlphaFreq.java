package homework6;

import java.util.Comparator;

/**
 * Sorts words according to alphabets first and if there is a tie, then words
 * are sorted by their frequencies in ascending order (a word with lowest
 * frequency comes first), implements Comparator interface.
 * Andrew ID: yuanyin
 *
 * @author EvaYin
 */
public class AlphaFreq implements Comparator<Word> {
    /**
     * Compare two Word objects based on alphabets first and if there is a tie,
     * sorts by their frequencies in ascending order, override compare() in
     * Comparator.
     *
     * @param o1 Word to be compared
     * @param o2 Word to be compared
     * @return int positive if o1 is larger, negative if o2 is larger
     */
    @Override
    public int compare(Word o1, Word o2) {
        if (o1.getWord().compareTo(o2.getWord()) == 0) {
            return Integer.compare(o1.getFrequency(), o2.getFrequency());
        }
        return o1.getWord().compareTo(o2.getWord());
    }
}
