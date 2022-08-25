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
    private final int row;

    private final List<String[]> data;

    AirportSearcher(int row) {
        data = readFile();
        sortByColumn(row, data);
        this.row = row;
    }

    public void search() {
        System.out.print("Введите строку: ");
        Scanner scanner = new Scanner(System.in);
        String forSearching = scanner.nextLine();
        int i = runBinarySearchIteratively(data, forSearching, 0, data.size() - 1);
        System.out.println(i);
    }

    public int runBinarySearchIteratively(List<String[]> sortedArray, String key, int low, int high) {
        int index = Integer.MAX_VALUE;
        LinesComparator linesComparator = new LinesComparator(row);

        while (low <= high) {
            int mid = low  + ((high - low) / 2);
            if (linesComparator.compare(sortedArray.get(mid), key) < 0 /*sortedArray[mid] < key*/) {
                low = mid + 1;
            } else if (linesComparator.compare(sortedArray.get(mid), key) > 0 /*sortedArray[mid] > key*/) {
                high = mid - 1;
            } else if (sortedArray.get(mid)[row].startsWith(key)/*linesComparator.compare(sortedArray.get(mid), key) == 0 *//*sortedArray[mid] == key*/) {
                index = mid;
                break;
            }
        }
        return index;
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String[] line : data) {
            sb.append(i).append("@").append(line[row]).append("\n");
            i++;
        }
        return sb.toString();
    }
}

