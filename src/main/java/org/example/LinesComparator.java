package org.example;

import java.util.Comparator;

public class LinesComparator implements Comparator<String[]> {
    private int row;

    LinesComparator(int row){
        this.row = row;
    }

    @Override
    public int compare(String[] o1, String[] o2) {
        String word1 = o1[row];
        String word2 = o2[row];
        return word1.compareTo(word2);
    }
}
