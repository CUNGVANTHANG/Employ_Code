package base;

import java.util.TreeMap;

public class Dictionary {
    protected static TreeMap<String, Word> dictionary = new TreeMap<>();

    private static String dataPath = "src/main/resources/database/dictionary.txt";

    public static String getDataPath() {
        return dataPath;
    }
}
