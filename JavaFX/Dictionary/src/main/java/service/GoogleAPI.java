package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleAPI {
    public static String response(String getUrl) throws IOException {
        URL obj = new URL(getUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }
        return null;
    }

    public static String request(String langFrom, String langTo, String text) throws UnsupportedEncodingException {
        String url = "https://translate.googleapis.com/translate_a/single" + "?client=t" +
                "&sl=" + langFrom +
                "&tl=" + langTo +
                "&hl=" + langTo +
                "&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&kc=1" +
                "&tk=" + generateToken(text) +
                "&q=" + URLEncoder.encode(text, "UTF-8");
        return url;
    }

    public static String request(String lang, String text) throws UnsupportedEncodingException {
        String url = "http://translate.google.com/translate_tts" + "?ie=UTF-8" +
                "&q=" + URLEncoder.encode(text, "UTF-8") +
                "&tl=" + lang +
                "&tk=" + generateToken(text) +
                "&client=t";
        return url;
    }

    private static String generateToken(String text) {
        int tkk[] = TKK();
        int b = tkk[0];
        int e = 0;
        int f = 0;
        List<Integer> d = new ArrayList<Integer>();
        for (; f < text.length(); f++) {
            int g = text.charAt(f);
            if (0x80 > g) {
                d.add(e++, g);
            } else {
                if (0x800 > g) {
                    d.add(e++, g >> 6 | 0xC0);
                } else {
                    if (0xD800 == (g & 0xFC00) && f + 1 < text.length() && 0xDC00 == (text.charAt(f + 1) & 0xFC00)) {
                        g = 0x10000 + ((g & 0x3FF) << 10) + (text.charAt(++f) & 0x3FF);
                        d.add(e++, g >> 18 | 0xF0);
                        d.add(e++, g >> 12 & 0x3F | 0x80);
                    } else {
                        d.add(e++, g >> 12 | 0xE0);
                        d.add(e++, g >> 6 & 0x3F | 0x80);
                    }
                }
                d.add(e++, g & 63 | 128);
            }
        }

        int a_i = b;
        for (e = 0; e < d.size(); e++) {
            a_i += d.get(e);
            a_i = RL(a_i, "+-a^+6");
        }
        a_i = RL(a_i, "+-3^+b+-f");
        a_i ^= tkk[1];
        long a_l;
        if (0 > a_i) {
            a_l = 0x80000000l + (a_i & 0x7FFFFFFF);
        } else {
            a_l = a_i;
        }
        a_l %= Math.pow(10, 6);
        return String.format(Locale.US, "%d.%d", a_l, a_l ^ b);
    }

    private static int[] TKK() {
        return new int[]{0x6337E, 0x217A58DC + 0x5AF91132};
    }

    private static int RL(int a, String b) {
        for (int c = 0; c < b.length() - 2; c += 3) {
            int d = b.charAt(c + 2);
            d = d >= 65 ? d - 87 : d - 48;
            d = b.charAt(c + 1) == '+' ? shr32(a, d) : (a << d);
            a = b.charAt(c) == '+' ? (a + (d & 0xFFFFFFFF)) : a ^ d;
        }
        return a;
    }

    private static int shr32(int x, int bits) {
        if (x < 0) {
            long x_l = 0xffffffffl + x + 1;
            return (int) (x_l >> bits);
        }
        return x >> bits;
    }
}
