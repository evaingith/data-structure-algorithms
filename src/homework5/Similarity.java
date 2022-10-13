package homework5;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 17683 Data Structures for Application Programmers. Homework Assignment 5
 * Document Similarity.
 * I chose hashmap because it is fast to search the key, map.containsKey(key)
 * is O(1) on average; use BigInteger because it is the most defensive way to
 * handle large files in this program to avoid overflow.
 *
 * Andrew ID: yuanyin
 * @author EvaYin
 */
public class Similarity {
    /**
     * map to store words and their frequencies.
     */
    private Map<String, BigInteger> wordFrequencyMap;
    /**
     * number of lines in the input file.
     */
    private int numOfLines;
    /**
     * Constructor for the wordFrequencyMap with input of string.
     * @param string String to be parsed to be stored in the map
     */
    public Similarity(String string) {
        wordFrequencyMap = new HashMap<String, BigInteger>();
        numOfLines = 0;
        if (string == null || string.length() == 0) {
            return;
        }
        String[] wordsInLine = string.split("\\W");
        for (String next : wordsInLine) {
            if (next.matches("[a-zA-Z]+")) {
                next = next.toLowerCase();
                if (wordFrequencyMap.containsKey(next)) {
                    BigInteger frequency = wordFrequencyMap.get(next);
                    wordFrequencyMap.put(next, frequency.add(BigInteger.valueOf(1)));
                } else {
                    wordFrequencyMap.put(next, BigInteger.valueOf(1));
                }
            }
        }
    }
    /**
     * Constructor for the wordFrequencyMap with input of file.
     * @param file file to be parsed to be stored in the map
     */
    public Similarity(File file) {
        wordFrequencyMap = new HashMap<String, BigInteger>();
        numOfLines = 0;
        Scanner scanner = null;
        try {
            if (file == null || file.length() == 0) {
                return;
            }
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                String[] wordsInLine = nextLine.split("\\W");
                for (String word : wordsInLine) {
                    if (word != null && !word.isEmpty() && word.matches("[a-zA-Z]+")) {
                        word = word.toLowerCase();
                        if (wordFrequencyMap.containsKey(word)) {
                            BigInteger frequency = wordFrequencyMap.get(word);
                            wordFrequencyMap.put(word, frequency.add(BigInteger.valueOf(1)));
                        } else {
                            wordFrequencyMap.put(word, BigInteger.valueOf(1));
                        }
                    }
                }
                numOfLines++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("empty files: " + file.getName());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

    }
    /**
     * Return number of lines.
     * @return int number of lines in file
     */
    public int numOfLines() {
        return numOfLines;
    }
    /**
     * Return number of words.
     * @return BigInteger number of words in file/string
     */
    public BigInteger numOfWords() {
        BigInteger res = BigInteger.valueOf(0);
        for (String key : wordFrequencyMap.keySet()) {
            BigInteger frequency = wordFrequencyMap.get(key);
            res = res.add(frequency);
        }
        return res;
    }
    /**
     * Return number of unique words.
     * @return int number of unique words in file/string
     */
    public int numOfWordsNoDups() {
        return wordFrequencyMap.size();
    }
    /**
     * Return euclideanNorm of words in the map.
     * @return double euclideanNorm of words in the map
     */
    public double euclideanNorm() {
        return euclideanNorm(wordFrequencyMap);
    }
    /**
     * Takes the map as a parameter,
     * return euclideanNorm of words in the map;
     * if null or empty input, return 0.
     * @param map map with words and frequencies
     * @return double euclideanNorm of words in the map
     */
    private double euclideanNorm(Map<String, BigInteger> map) {
        if (map == null || map.size() == 0) {
            return 0;
        }
        double res = 0;
        for (String key : map.keySet()) {
            BigInteger freq = map.get(key);
            res += freq.doubleValue() * freq.doubleValue();
        }
        return Math.sqrt(res);
    }
    /**
     * On average, the running time of dotProduct method is O(n),
     * because for loop is O(n) and if (map.containsKey(key)) is O(1),
     * in the worst case, if (map.containsKey(key)) could be O(n),
     * but O(1) on average, so it will not fall into the quadratic
     * running time complexity on average.
     * Calculate the dot product of word frequencies,
     * if null or empty input, return 0.
     * @param map map with words and frequencies
     * @return double dot product of words in the map
     */
    public double dotProduct(Map<String, BigInteger> map) {
        if (map == null || wordFrequencyMap == null || map.size() == 0 || wordFrequencyMap.size() == 0) {
            return 0;
        }
        BigInteger res = BigInteger.valueOf(0);
        for (String key : wordFrequencyMap.keySet()) {
            if (map.containsKey(key)) {
                res = res.add(map.get(key).multiply(wordFrequencyMap.get(key)));
            }
        }
        return res.doubleValue();
    }
    /**
    * Calculate the distance between two strings/files,
    * if null or empty input, return Math.PI / 2,
    * if two are identical, return 0.
    * @param map map with words and frequencies
    * @return double distance between two strings/files
    */
    public double distance(Map<String, BigInteger> map) {
        if (map == null || wordFrequencyMap == null || map.size() == 0 || wordFrequencyMap.size() == 0) {
            return Math.PI / 2;
        } else if (isIdentical(map)) {
            return 0;
        }
        return Math.acos(dotProduct(map) / (euclideanNorm() * euclideanNorm(map)));
    }
    /**
     * Return true if two maps are identical, false if not identical.
     * @param map map with words and frequencies
     * @return boolean true if two maps are identical, false if not
     */
    private boolean isIdentical(Map<String, BigInteger> map) {
        if (map.equals(wordFrequencyMap)) {
            return true;
        }
        return false;
    }
    /**
     * Return a shallow copy map of wordFrequencyMap.
     * @return map copy of wordFrequencyMap
     */
    public Map<String, BigInteger> getMap() {
        Map<String, BigInteger> map = new HashMap<String, BigInteger>();
        for (String word : wordFrequencyMap.keySet()) {
            String key = new String(word);
            BigInteger value = new BigInteger(wordFrequencyMap.get(word).toString());
            map.put(key, value);
        }
        return map;
    }
}
