package homework6;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Constructor BST object and getter, setter, search, insert and an in-order
 * iterative iterator, BST extends Comparable<T>, implements Iterable<T>,
 * BSTInterface<T>, override search, insert and iterator.
 * Andrew ID: yuanyin
 *
 * @author EvaYin
 * @param <T> comparable object
 */
public class BST<T extends Comparable<T>> implements Iterable<T>, BSTInterface<T> {
    /**
     * Root of BST.
     */
    private Node<T> root = null;
    /**
     * Comparator chose by user in Index.java.
     */
    private Comparator<T> comparator = null;

    /**
     * Construct BST object.
     */
    public BST() {
        this(null);
    }

    /**
     * Construct BST object with input comparator.
     *
     * @param comp comparator used in BST object
     */
    public BST(Comparator<T> comp) {
        comparator = comp;
        root = null;
    }

    /**
     * Get comparator used in BST object.
     *
     * @return Comparator<T> comparator used in BST object
     */
    public Comparator<T> comparator() {
        return comparator;
    }

    /**
     * Get root data of BST object, return null if null root.
     *
     * @return T root data of BST object
     */
    public T getRoot() {
        if (root == null) {
            return null;
        }
        return root.data;
    }

    /**
     * Get height of BST object recursively, return 0 if null root or only root.
     *
     * @return int height of BST object
     */
    public int getHeight() {
        if (root == null || (root.left == null && root.right == null)) {
            return 0;
        }
        return getHeight(root) - 1;
    }

    /**
     * Get height of BST object recursively, return 0 if null root or only root;
     * base case: root is null or reach leaf node; recursion: get the height of
     * deeper side of left and right as height.
     *
     * @param node Node<T> on BST to be recursed
     * @return int height of BST object
     */
    private int getHeight(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    /**
     * Get number of nodes in BST object recursively.
     *
     * @return int number of nodes in BST object
     */
    public int getNumberOfNodes() {
        return getNumberOfNodes(root);
    }

    /**
     * Get number of nodes in BST object recursively; base case: root is null or
     * reach leaf node; recursion: get number of nodes from both left and right.
     *
     * @param node Node<T> on BST to be recursed
     * @return int number of nodes in BST object
     */
    private int getNumberOfNodes(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + getNumberOfNodes(node.left) + getNumberOfNodes(node.right);
    }

    /**
     * Recursively searches the BST, compare objects in the BST either using
     * compareTo() or compare().
     *
     * @param toSearch T value (object) to be searched
     * @return T value (object) of the search result, if not found, null.
     */
    @Override
    public T search(T toSearch) {
        if (search(root, toSearch) == null) {
            return null;
        }
        return search(root, toSearch).data;
    }

    /**
     * Recursively searches the BST, compare objects in the BST either using
     * compareTo() or compare(); base case: find the value (object) or reach leaf
     * nodes; recursion: if the value of node is larger than toSearch, search left
     * side, otherwise search right side.
     *
     * @param toSearch T value (object) to be searched
     * @param node     Node<T> on BST to be recursed
     * @return T value (object) of the search result, if not found, null
     */
    private Node<T> search(Node<T> node, T toSearch) {
        if (comparator != null) {
            if (node == null || comparator.compare(node.data, toSearch) == 0) {
                return node;
            }
            if (comparator.compare(node.data, toSearch) > 0) {
                return search(node.left, toSearch);
            } else {
                return search(node.right, toSearch);
            }
        } else {
            if (node == null || node.data.compareTo(toSearch) == 0) {
                return node;
            }
            if (node.data.compareTo(toSearch) > 0) {
                return search(node.left, toSearch);
            } else {
                return search(node.right, toSearch);
            }
        }
    }

    /**
     * Accepts any comparable object toInsert and recursively inserts the object
     * into the BST based on either natural ordering (provided by the Comparable
     * interface) or the specified comparator, passed into the tree through a
     * constructor. if there is already the existing object, such as a word (String
     * value), in the BST, keep the old one. Inserts a value (object) to the tree,
     * override insert() no duplicates.
     *
     * @param toInsert a value (object) to insert into the tree
     */
    @Override
    public void insert(T toInsert) {
        root = insert(root, toInsert);
    }

    /**
     * Accepts any comparable object toInsert and recursively inserts the object
     * into the BST based on either natural ordering (provided by the Comparable
     * interface) or the specified comparator, passed into the tree through a
     * constructor. if there is already the existing object, such as a word (String
     * value), in the BST, keep the old one. Inserts a value (object) to the tree,
     * override insert(), no duplicates. Base case: find the place to insert the
     * node; recursion: compare node data to toInsert, if node is larger, go to left
     * side, otherwise, go to right side.
     *
     * @param toInsert a value (object) to insert into the tree
     * @param node Node<T> on BST to be recursed
     * @return T value (object) of the insert result
     */
    private Node<T> insert(Node<T> node, T toInsert) {
        if (node == null) {
            node = new Node<T>(toInsert);
            return node;
        }
        if (comparator != null) {
            if (comparator.compare(node.data, toInsert) > 0) {
                node.left = insert(node.left, toInsert);
            } else if (comparator.compare(node.data, toInsert) < 0) {
                node.right = insert(node.right, toInsert);
            }
        } else {
            if (node.data.compareTo(toInsert) > 0) {
                node.left = insert(node.left, toInsert);
            } else if (node.data.compareTo(toInsert) < 0) {
                node.right = insert(node.right, toInsert);
            }
        }
        return node;
    }

    /**
     * Iteratively in-order iterator that traverses the elements of the BST in an
     * ordered sequence, override iterator, hasNext and next.
     *
     * @return Iterator<T> iterator that traverses the elements of the BST
     */
    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {
            /**
             * Current node to be iterated.
             */
            private Node<T> curr = root;
            /**
             * Stack to store the nodes.
             */
            private Stack<Node<T>> stack = new Stack<>();

            /**
             * Check if next node is available.
             *
             * @return boolean true if next node is available
             */
            @Override
            public boolean hasNext() {
                return (!stack.isEmpty() || curr != null);
            }

            /**
             * Return value(object) of next node, if not available, throw new
             * NoSuchElementException().
             *
             * @return T value(object) of next node
             */
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (root == null) {
                    return null;
                }
                while (curr != null) {
                    stack.push(curr);
                    curr = curr.left;
                }
                curr = stack.pop();
                T t = curr.data;
                curr = curr.right;
                return t;
            }
        };
        return it;
    }

    /**
     * Node class in BST.
     *
     * @param <T> comparable object
     */
    private static class Node<T> {
        /**
         * Data of node.
         */
        private T data;
        /**
         * Left child of node.
         */
        private Node<T> left;
        /**
         * Right child of node.
         */
        private Node<T> right;

        /**
         * Construct Node object with data of d.
         *
         * @param d T data of node
         */
        Node(T d) {
            this(d, null, null);
        }

        /**
         * Construct Node object with data of d.
         *
         * @param d T data of node
         * @param l Node<T> left child of node
         * @param r Node<T> right child of node
         */
        Node(T d, Node<T> l, Node<T> r) {
            data = d;
            left = l;
            right = r;
        }
    }
}
