package homework6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Building an index tree in three different ways using three different
 * methods.validate words A word is a sequence of letters [a..zA..Z] that does
 * not include digits [0..9] and does not include other characters than the
 * sequence of letters [a..zA..Z]. If IgnoreCase comparator is passed into
 * buildIndex method, then all the words are converted into lower case and then
 * added into the BST.
 * Andrew ID: yuanyin
 *
 * @author EvaYin
 */
public class Index {
    /**
     * Pares an input text file (using the same order as they appear in a file) and
     * build an index tree using a natural alphabetical order.
     *
     * @param fileName String input by user
     * @return BST<Word> BST with content of the file
     */
    public BST<Word> buildIndex(String fileName) {
        return buildIndex(fileName, null);
    }

    /**
     * Parses an input text file (using the same order as they appear in a file) and
     * build an index tree using a specific ordering among words provided by a
     * specific comparator.
     *
     * @param fileName   String input by user
     * @param comparator Comparator<Word> input by user
     * @return BST<Word> BST with content of the file
     */
    public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) {
        BST<Word> bst = new BST<Word>(comparator);
        File file = new File(fileName);
        Scanner scanner = null;
        int lineNumber = 0;
        try {
            if (file == null || file.length() == 0) {
                return null;
            }
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                lineNumber++;
                String[] wordsInLine = nextLine.split("\\W");
                for (String word : wordsInLine) {
                    if (word != null && !word.isEmpty() && word.matches("[a-zA-Z]+")) {
                        if (comparator instanceof IgnoreCase) {
                            word = word.toLowerCase();
                        }
                        Word wordObj = new Word(word);
                        Word oldWordObj = bst.search(wordObj);
                        if (oldWordObj != null) {
                            oldWordObj.setFrequency(oldWordObj.getFrequency() + 1);
                            oldWordObj.addToIndex(lineNumber);
                        } else {
                            bst.insert(wordObj);
                            wordObj.setFrequency(1);
                            wordObj.addToIndex(lineNumber);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("empty files: " + fileName);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return bst;
    }

    /**
     * Takes Word objects in a list from the first to the end and allows to rebuild
     * an index tree using a different ordering specified by a comparator. If there
     * is already the existing object in the BST, simply keep the existing one, no
     * update or combine.
     *
     * @param list       ArrayList<Word> input by user
     * @param comparator Comparator<Word> input by user
     * @return BST<Word> BST with content of the list
     */
    public BST<Word> buildIndex(ArrayList<Word> list, Comparator<Word> comparator) {
        BST<Word> bst = new BST<Word>(comparator);
        for (Word wordObj : list) {
            if (bst.search(wordObj) != null) {
                wordObj.setFrequency(wordObj.getFrequency() + 1);
                for (int index : wordObj.getIndex()) {
                    wordObj.addToIndex(index);
                }
            } else {
                Word wordObject = new Word(wordObj.getWord());
                bst.insert(wordObject);
                wordObject.setFrequency(wordObj.getFrequency());
                for (int index : wordObj.getIndex()) {
                    wordObject.addToIndex(index);
                }
            }
        }
        return bst;
    }

    /**
     * Sorts words according to alphabets first and if there is a tie, then words
     * are sorted by their frequencies in ascending order (a word with lowest
     * frequency comes first), not modify the tree structure of the argument, BST.
     *
     * @param tree BST<Word> input by user
     * @return ArrayList<Word> sorted list of BST
     */
    public ArrayList<Word> sortByAlpha(BST<Word> tree) {
        ArrayList<Word> listByAlpha = new ArrayList<Word>();
        Comparator<Word> comparator = new AlphaFreq();
        Iterator<Word> treeItr = tree.iterator();
        while (treeItr.hasNext()) {
            listByAlpha.add(treeItr.next());
        }
        /*
         * Even though there should be no ties with regard to words in BST, in the
         * spirit of using what you wrote, use AlphaFreq comparator in this method.
         */
        Collections.sort(listByAlpha, comparator);
        return listByAlpha;
    }

    /**
     * Sorts words according to their frequencies (a word with highest frequency
     * comes first), not modify the tree structure of the argument, BST.
     *
     * @param tree BST<Word> input by user
     * @return ArrayList<Word> sorted list of BST
     */
    public ArrayList<Word> sortByFrequency(BST<Word> tree) {
        ArrayList<Word> listByFreq = new ArrayList<Word>();
        Comparator<Word> comparator = new Frequency();
        Iterator<Word> treeItr = tree.iterator();
        while (treeItr.hasNext()) {
            listByFreq.add(treeItr.next());
        }
        Collections.sort(listByFreq, comparator);
        return listByFreq;
    }

    /**
     * Sorts words according to their frequencies (a word with highest frequency
     * comes first), return ones with highest frequency, not modify the tree
     * structure of the argument, BST.
     *
     * @param tree BST<Word> input by user
     * @return ArrayList<Word> sorted list of BST with highest frequency
     */
    public ArrayList<Word> getHighestFrequency(BST<Word> tree) {
        ArrayList<Word> listByFreq = sortByFrequency(tree);
        int highestFreq = listByFreq.get(0).getFrequency();
        ArrayList<Word> highFreq = new ArrayList<Word>();
        for (Word word : listByFreq) {
            if (word.getFrequency() == highestFreq) {
                highFreq.add(word);
            }
        }
        return highFreq;
    }
}
