package org.example.utils;

public class ArgumentParser {
    public static int parseArgument(String[] args) {
        int row;

        try {
            row = Integer.parseInt(args[0]);

            if (row > 14 || row <= 0) {
                throw new NumberFormatException();
            }

            row--; /* Нужно для смещения под индексацию массива */
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Некорректно введен номер столбца");
        }

        return row;
    }
}
