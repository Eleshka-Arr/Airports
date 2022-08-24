package org.example;

public class Main {
    public static void main(String[] args) {
        int row = 0;
        try {
            row = Integer.parseInt(args[0]);

            if (row > 14 || row <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException exception) {
            System.out.println("Некорректно введен номер столбца");
        }

        AirportSearcher airportSearcher = new AirportSearcher(row);
    }
}