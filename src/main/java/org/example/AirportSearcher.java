package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AirportSearcher {
    private static final String CSV_NAME = "airports.csv";
    private final int row;

    AirportSearcher(int row) {
        this.row = row;
    }

    public void start() {
        while (true) {
            System.out.print("Введите строку: ");
            Scanner scanner = new Scanner(System.in);
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

    private SearchingResult<List<String[]>> search(String forSearching) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource(CSV_NAME)).getFile());
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

        return new SearchingResult<>(result, finish - start);
    }
}

