package homework6;

import java.util.Set;
import java.util.TreeSet;

/**
 * Constructor Word object and getter, setter and addToIndex methods, implements
 * Comparable, override toString and compareTo.
 * Andrew ID: yuanyin
 *
 * @author EvaYin
 */
public class Word implements Comparable<Word> {
    /**
     * Word string in Word object.
     */
    private String word;
    /**
     * Line numbers of each word.
     */
    private Set<Integer> index = new TreeSet<Integer>();
    /**
     * Frequency of this word.
     */
    private int frequency;

    /**
     * Construct Word object with word string.
     *
     * @param wordStr String to be set
     */
    Word(String wordStr) {
        this.word = wordStr;
    }

    /**
     * Set string as input newWord.
     *
     * @param newWord String to be set
     */
    public void setWord(String newWord) {
        word = newWord;
    }

    /**
     * Get word string of Word object.
     *
     * @return String word string of Word object
     */
    public String getWord() {
        return word;
    }

    /**
     * Set frequency as input freq.
     *
     * @param freq int frequency to be set
     */
    public void setFrequency(int freq) {
        frequency = freq;
    }

    /**
     * Get frequency of Word object.
     *
     * @return int frequency of Word object
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Add new line number for the word into index.
     *
     * @param line line number for the word to be added
     */
    public void addToIndex(Integer line) {
        index.add(line);
    }

    /**
     * Get set of index, line numbers, of Word object.
     *
     * @return Set<Integer> contains all of the line numbers for the word.
     */
    public Set<Integer> getIndex() {
        return index;
    }

    /**
     * Returns string of content of Word object.
     *
     * @return String formatted as HW6Driver indicates
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(word).append(" ").append(frequency).append(" ").append(index);
        return sb.toString();
    }

    /**
     * Compare this word with input word object, natural order is defined by
     * alphabetical order of the word field (String).
     *
     * @param o word to be compared
     * @return int positive if this is larger, negative if o is larger
     */
    @Override
    public int compareTo(Word o) {
        return this.word.compareTo(o.word);
    }
}
