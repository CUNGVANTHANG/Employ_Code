package service;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class GoogleTranslate extends GoogleAPI {
    public static String translate(String langFrom, String langTo, String text) throws IOException, ParseException {
        String url = request(langFrom, langTo, text);
        String result = response(url);

        JSONArray jsonArray = (JSONArray) new JSONParser().parse(result);
        JSONArray translation = (JSONArray) jsonArray.get(0);

        JSONArray firstTranslation = (JSONArray) translation.get(0);
        String translated = (String) firstTranslation.get(0);

        return translated;
    }
}