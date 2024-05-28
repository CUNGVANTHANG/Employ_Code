package base;

public class Bookmark extends DictionaryManagement {
    private static String dataPath = "src/main/resources/database/bookmark.txt";

    public static void insertFromFile() {
        insertFromFile(getDataPath());
    }

    public static void dictionaryExportToFile() {
        dictionaryExportToFile(getDataPath());
    }

    public static String getDataPath() {
        return dataPath;
    }
}
