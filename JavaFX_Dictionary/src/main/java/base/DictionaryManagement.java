package base;

import java.io.*;
import java.util.ArrayList;

public class DictionaryManagement extends Dictionary {

    public static String getAbsolutePath(String dataPath) {
        String currentDirectory = System.getProperty("user.dir");
        currentDirectory = currentDirectory.replace("\\", "/");

        return currentDirectory + dataPath;
    }

    public static void insertFromFile(String dataPath) {
        try {
            FileReader fileReader = new FileReader(getAbsolutePath(dataPath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String wordExplain = "";
                String wordTarget = "";

                if (line.indexOf("|") != -1) {
                    wordTarget += line.substring(0, line.indexOf("|")).trim();
                    wordExplain += line.substring(line.indexOf("|") + 1).trim();
                    dictionary.put(wordTarget.toLowerCase(), new Word(wordTarget, wordExplain));
                } else {
                    wordTarget += line;
                    dictionary.put(wordTarget.toLowerCase(), new Word(wordTarget, null));
                }
            }

            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertFromFile() {
        insertFromFile(getDataPath());
    }

    public static void dictionaryExportToFile(String dataPath) {
        try {
            FileWriter fileWriter = new FileWriter(getAbsolutePath(dataPath));
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Word word : dictionary.values()) {
                bufferedWriter.write(word.getWordTarget() + " | " + word.getWordExplain() + "\n");
            }

            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dictionaryExportToFile() {
        dictionaryExportToFile(getDataPath());
    }

    public static String dictionaryLookup(String wordTarget) {
        Word word = dictionary.get(wordTarget.toLowerCase().trim());
        if (word != null) {
            return word.getWordExplain();
        } else {
            return null;
        }
    }

    public static ArrayList<String> dictionarySearcher(String prefix) {
        ArrayList<String> resultList = new ArrayList<>(dictionary
                .subMap(prefix, prefix + Character.MAX_VALUE)
                .keySet()
                .stream()
                .toList());

        return resultList;
    }

    public static void removeWord(String word_target) {
        if (dictionary.containsKey(word_target.toLowerCase())) {
            dictionary.remove(word_target.toLowerCase());
            System.out.println("Deleted words...");
        } else {
            System.out.println("Does not exist");
        }

        dictionaryExportToFile();
    }

    public static void addWord(String word_target, String word_explain) {
        if (dictionary.containsKey(word_target.toLowerCase())) {
            System.out.println("Word exists");
        } else {
            dictionary.put(word_target.toLowerCase(), new Word(word_target, word_explain));
            System.out.println("Added words...");
        }

        dictionaryExportToFile();
    }

    public static void modifyWord(String word_target, String word_target_modify, String word_explain) {
        if (dictionary.containsKey(word_target.toLowerCase())) {
            Word word = dictionary.get(word_target.toLowerCase());
            word.setWordTarget(word_target_modify);
            word.setWordExplain(word_explain);
            dictionary.put(word_target, word);
            System.out.println("Corrected words...");
        } else {
            System.out.println("Does not exist");
        }

        dictionaryExportToFile();
    }

    public static void dictionaryUpdate() {
        dictionary.clear();
        insertFromFile();
    }

    public static void dictionaryClear() {
        System.out.println("Close...");
        dictionary.clear();
    }
}
