package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AirportSearcher {
    private static final String CSV_PATH = "airports.csv";

    private final List<String[]> data;

    AirportSearcher(int row) {
        data = readFile();
        sortByColumn(row, data);
    }

    public void search(int row) {
        System.out.print("Введите строку: ");
        Scanner scanner = new Scanner(System.in);
        String forSearching = scanner.nextLine();
    }

    private List<String[]> readFile() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(CSV_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

        Scanner scanner = new Scanner(fis, StandardCharsets.UTF_8);
        List<String[]> result = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            String[] line = str.split(",");
            result.add(line);
        }

        return result;
    }

    private void sortByColumn(int row, List<String[]> list) {
        Collections.sort(list, new LinesComparator(row));
    }

}
