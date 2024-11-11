package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AmazonService {
    public static final Pattern PRODUCT_PATTERN = Pattern.compile("<span class=\"a-size-medium a-color-base a-text-normal\">([^<]+)<\\/span>.*<span class=\\\"a-icon-alt\\\">([^<]+)<\\/span>.*<span class=\\\"a-offscreen\\\">([^<]+)<\\/span>");


    public String searchProducts(String keyword) throws IOException {
        return parseProductHtml(getProductHtml(keyword));
    }

    private String parseProductHtml(String html) {
        String res = "";
        Matcher matcher = PRODUCT_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) + " - " + matcher.group(2) + ", price:" + matcher.group(3) + "<br>\n";
        }
        return res;
    }

    private String getProductHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.amazon.com/s?k=" + keyword + "&crid=2BYRERBID490G&sprefix=%2Caps%2C58&ref=nb_sb_ss_recent_1_0_recent")
                .method("GET", null)
                .addHeader("Cookie", "session-token=If1GghYFXYMfmEkHPcto+10LdP9xijgvNJwGbAc19OVBoeDv9l2vpTmjlT73P/hc8KK6jIcZ6kluI6gk8L00QR8FwcBdwVa6dQkDKxO3qdH4MSQDf8logSTnRkOx9w7gM4soiYXMPQK2aKWDrjlBZcdHP/dMoG14mrLBip3FrP9Z4IdBKIelGyXJxSWKUV7s5YBGPMZa9fMdORzmwhz82Nzmmwr5E7sIN35b20oKKPdqvNtZ/cFFezq3Sr5uLbonmJjJYmFPvPtQNcs0wY72xI3WDzzR2mqIUhXcIQK0VR0Gz4pXN03JnE58NdVPZZPEWaf5Yl68WhpKACoCURqmoXndIHrIb0p/")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
