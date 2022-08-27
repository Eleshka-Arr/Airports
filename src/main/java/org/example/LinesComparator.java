package org.example;

import java.util.Comparator;

public class LinesComparator implements Comparator<String[]> {
    private final int row;

    LinesComparator(int row){
        this.row = row;
    }

    @Override
    public int compare(String[] o1, String[] o2) {
        String word1 = o1[row];
        String word2 = o2[row];
        Integer num1;
        int num2;

        try {
            num1 = Integer.parseInt(word1);
            num2 = Integer.parseInt(word2);
        } catch (NumberFormatException exception) {
            return word1.compareTo(word2);
        }

        return num1.compareTo(num2);
    }
}
