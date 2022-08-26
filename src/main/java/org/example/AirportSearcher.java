package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AirportSearcher {
    private static final String CSV_NAME = "airport.csv";
    private final int row;
    private final String csv;

    AirportSearcher(int row) {
        this.row = row;
        URL url = getClass().getClassLoader().getResource(CSV_NAME);
        if (url == null) {
            throw new IllegalArgumentException("Файл с именем " + CSV_NAME + " не найден");
        }
        csv = url.getFile();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите строку: ");

            String forSearching = scanner.nextLine();

            if ("!quit".equals(forSearching)) {
                return;
            }

            SearchingResult<List<String[]>> result = searchAndSort(forSearching);
            System.out.println(resultToString(result));
        }
    }


    public SearchingResult<List<String[]>> searchAndSort(String forSearching) {
        SearchingResult<List<String[]>> results = search(forSearching);
        results.getResult().sort(new LinesComparator(row));
        return results;
    }

    public String resultToString(SearchingResult<List<String[]>> result) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String[] line : result.getResult()) {
            stringBuilder.append(line[row]).append("[");
            for (int i = 0; i < line.length; i++) {
                stringBuilder.append(line[i]);
                if(i != line.length - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append("]").append("\n");
        }

        stringBuilder.append("Количество найденных строк: ").append(result.getResult().size()).append(" ");
        stringBuilder.append("Время, затраченное на поиск: ").append(result.getTime()).append("\n\n");

        return stringBuilder.toString();
    }

    private SearchingResult<List<String[]>> search(String forSearching) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(csv);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

        Scanner scanner = new Scanner(fis, StandardCharsets.UTF_8);
        List<String[]> result = new ArrayList<>();

        long start = System.currentTimeMillis();

        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            String[] line = str.split(",");

            if(line[row].startsWith(forSearching)) {
                result.add(line);
            }
        }

        long finish = System.currentTimeMillis();

        try {
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new SearchingResult<>(result, finish - start);
    }
}

