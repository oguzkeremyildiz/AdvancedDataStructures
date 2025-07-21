import Cookies.Tuple.Pair;

import java.util.*;

public class SuffixTree {

    private final SuffixNode root;
    private final String text;

    public SuffixTree(String text) {
        this.text = text;
        int[] suffixArray = generateSuffixArray(text);
        int[] lcpArray = generateLCPArray(suffixArray, text);
        this.root = new SuffixNode();
        buildSuffixTree(suffixArray, lcpArray);
    }

    private void buildSuffixTree(int[] suffixArray, int[] lcpArray) {
        SuffixNode first = new SuffixNode(suffixArray[0], 0, text.length() - suffixArray[0], root);
        root.addChild(first, text);
        SuffixNode currentNode = first;
        for (int i = 1; i < suffixArray.length; i++) {
            // finding parent of the new node.
            SuffixNode prevNode = null;
            while (currentNode.startChar() >= lcpArray[i - 1]) {
                prevNode = currentNode;
                currentNode = currentNode.getParent();
            }
            SuffixNode newNode;
            // adding to the last node.
            if (prevNode == null) {
                if (currentNode.index() + lcpArray[i - 1] != text.length()) {
                    currentNode.setEndChar(lcpArray[i - 1]);
                }
                currentNode.addChild(new SuffixNode(currentNode.index() + currentNode.endChar(), 0, text.length() - currentNode.index() - currentNode.endChar(), currentNode), text);
                newNode = new SuffixNode(suffixArray[i], lcpArray[i - 1], text.length() - suffixArray[i], currentNode);
                currentNode.addChild(newNode, text);
            } else {
                if (currentNode == root) {
                    newNode = new SuffixNode(suffixArray[i], lcpArray[i - 1], text.length() - suffixArray[i], currentNode);
                    currentNode.addChild(newNode, text);
                } else {
                    if (lcpArray[i - 1] == currentNode.endChar()) {
                        newNode = new SuffixNode(suffixArray[i], lcpArray[i - 1], text.length() - suffixArray[i], currentNode);
                        currentNode.addChild(newNode, text);
                    } else {
                        int textIndex = currentNode.index() + currentNode.startChar();
                        int startChar = currentNode.startChar();
                        currentNode.setStartChar(lcpArray[i - 1]);
                        SuffixNode splitNode = new SuffixNode(currentNode.index(), startChar, currentNode.startChar(), currentNode.getParent());
                        currentNode.getParent().setChild(text.charAt(textIndex), splitNode, text);
                        currentNode.setParent(splitNode);
                        splitNode.addChild(currentNode, text);
                        newNode = new SuffixNode(suffixArray[i], lcpArray[i - 1], text.length() - suffixArray[i], splitNode);
                        splitNode.addChild(newNode, text);
                    }
                }
            }
            currentNode = newNode;
        }
    }

    // Kasai's Algorithm
    // TC: O(n)
    private int[] generateLCPArray(int[] suffixArray, String text) {
        int[] lcp = new int[suffixArray.length];
        int[] inverseSuffixArray = new int[suffixArray.length];
        for (int i = 0; i < suffixArray.length; i++) {
            inverseSuffixArray[suffixArray[i]] = i;
        }
        int k = 0;
        for (int i = 0; i < suffixArray.length; i++) {
            if (inverseSuffixArray[i] == suffixArray.length - 1) {
                k = 0;
            } else {
                int j = suffixArray[inverseSuffixArray[i] + 1];
                while (i + k < suffixArray.length && j + k < suffixArray.length && text.charAt(i + k) == text.charAt(j + k)) {
                    k++;
                }
                lcp[inverseSuffixArray[i]] = k;
                if (k > 0) {
                    k--;
                }
            }
        }
        return lcp;
    }

    // Prefix Doubling Method
    // TC: O(nlogn)
    private int[] generateSuffixArray(String text) {
        int[] suffixes = new int[text.length()];
        HashMap<Integer, Integer>  rank = new HashMap<>();
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < text.length(); i++) {
            suffixes[i] = i;
            if (text.charAt(i) < min) {
                min = text.charAt(i);
            }
        }
        for (int i = 0; i < text.length(); i++) {
            rank.put(i, text.charAt(i) - min);
        }
        for (int k = 1; k < suffixes.length; k *= 2) {
            radixSort(suffixes, rank, k);
            HashMap<Integer, Integer> tempRank = new HashMap<>();
            tempRank.put(suffixes[0], 0);
            for (int i = 1; i < text.length(); i++) {
                if (rank.get(suffixes[i]).equals(rank.get(suffixes[i - 1])) && rank.getOrDefault(suffixes[i] + k, -1).equals(rank.getOrDefault(suffixes[i - 1] + k, -1))) {
                    tempRank.put(suffixes[i], tempRank.get(suffixes[i - 1]));
                } else {
                    tempRank.put(suffixes[i], tempRank.get(suffixes[i - 1]) + 1);
                }
            }
            rank = tempRank;
        }
        return suffixes;
    }

    private static void radixSort(int[] suffixes, HashMap<Integer, Integer> rank, int k) {
        for (int i = 1; i >= 0; i--) {
            int max = Integer.MIN_VALUE;
            for (int j = 0; j < suffixes.length; j++) {
                if (rank.get(j) > max) {
                    max = rank.get(j);
                }
            }
            int maxLength = 0;
            do {
                maxLength++;
                max /= 10;
            } while (max != 0);
            for (int j = 0; j < maxLength; j++) {
                ArrayList<Integer>[] countingSort = new ArrayList[11];
                for (int index = 0; index < 11; index++) {
                    countingSort[index] = new ArrayList<>();
                }
                for (int suffix : suffixes) {
                    if (suffix + k * i < suffixes.length) {
                        int value = rank.get(suffix + k * i);
                        countingSort[(1 + ((int) (value / Math.pow(10, j)) % 10))].add(suffix);
                    } else {
                        countingSort[0].add(suffix);
                    }
                }
                int iterate = 0;
                for (ArrayList<Integer> integers : countingSort) {
                    if (!integers.isEmpty()) {
                        for (Integer integer : integers) {
                            suffixes[iterate] = integer;
                            iterate++;
                        }
                    }
                }
            }
        }
    }

    private int findNextIndex(SuffixNode node, String pattern, int currentIndex) {
        for (int i = node.index() + node.startChar(); i < node.index() + node.endChar(); i++) {
            if (pattern.length() == currentIndex + i - node.index() - node.startChar()) {
                return pattern.length();
            }
            if (text.charAt(i) != pattern.charAt(currentIndex + i - node.index() - node.startChar())) {
                return -1;
            }
        }
        return currentIndex + node.endChar() - node.startChar();
    }

    private boolean search(String pattern, int currentIndex, SuffixNode currentNode) {
        SuffixNode child = currentNode.getChild(pattern.charAt(currentIndex));
        if (child == null) {
            return false;
        }
        currentIndex = findNextIndex(child, pattern, currentIndex);
        if (currentIndex == pattern.length()) {
            return true;
        }
        if (currentIndex == -1) {
            return false;
        }
        return search(pattern, currentIndex, child);
    }

    public boolean search(String pattern) {
        if (pattern.isEmpty()) {
            return true;
        }
        return search(pattern, 0, root);
    }

    private Pair<Integer, Integer> dfs(SuffixNode current, int count) {
        Pair<Integer, Integer> best;
        if (current.childCount() > 1) {
            best = new Pair<>(current.index(), count + current.endChar() - current.startChar());
        } else {
            return new Pair<>(-1, Integer.MIN_VALUE);
        }
        for (int i = 0; i < current.childCount(); i++) {
            Pair<Integer, Integer> p = dfs(current.getChild(i), count + current.endChar() - current.startChar());
            if (p.getValue() > best.getValue()) {
                best = p;
            }
        }
        return best;
    }

    public String findLongestRepeatedSubstring() {
        int startIndex = dfs(root, 0).getKey();
        StringBuilder str = new StringBuilder();
        for (int i = startIndex; i < text.length(); i++) {
            str.append(text.charAt(i));
        }
        return str.toString();
    }
}
