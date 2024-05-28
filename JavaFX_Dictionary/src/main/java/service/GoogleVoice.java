package service;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleVoice extends GoogleAPI{
    public static void speak(String language, String text) throws IOException {
        String url = request(language, text);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream audioSrc = con.getInputStream();
            try {
                new Player(new BufferedInputStream(audioSrc)).play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(request("en","Hello"));
        speak("en", "hello");
    }
}
