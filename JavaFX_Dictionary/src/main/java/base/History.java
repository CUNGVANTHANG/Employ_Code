package base;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class History extends DictionaryManagement {
    private static String dataPath = "src/main/resources/database/history.txt";

    public static void insertFromFile() {
        insertFromFile(getDataPath());
    }

    public static void dictionaryExportToFile() {
        dictionaryExportToFile(getDataPath());
    }

    public static String getDataPath() {
        return dataPath;
    }

    public static ArrayList<String> showHistory() {
        ArrayList<String> result = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(getAbsolutePath(dataPath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line.substring(0, line.indexOf(" | ")).trim());
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.reverse(result);

        return result;
    }

    public static void addWord(String wordTarget, String wordExplain) {
        ArrayList<String> history = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(DictionaryManagement.getAbsolutePath(dataPath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                history.add(line);
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fileWriter = new FileWriter(DictionaryManagement.getAbsolutePath(dataPath));
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (int i = 0; i < history.size(); i++) {
                if (history.get(i).substring(0, history.get(i).indexOf(" | ")).trim().equals(wordTarget)) {
                    history.remove(i);
                }
            }

            history.add(wordTarget + " | " + wordExplain);

            for (String word : history) {
                bufferedWriter.write(word + "\n");
            }

            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeWord(String wordTarget) {
        if (dictionary.containsKey(wordTarget.toLowerCase())) {
            dictionary.remove(wordTarget.toLowerCase());
        }
        dictionaryExportToFile();
    }
}
