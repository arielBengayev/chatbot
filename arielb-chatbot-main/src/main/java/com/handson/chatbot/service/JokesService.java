package com.handson.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JokesService {
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Autowired
    ObjectMapper om;

    public String searchJoke(String keyword) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.chucknorris.io/jokes/random?category=" + keyword)
                .method("GET", null)
                .addHeader("authority", "api.chucknorris.io")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "_ga=GA1.2.1483324706.1729538054; MCPopupClosed=yes; _gid=GA1.2.758904373.1729845644; _ga_JRZZDJK0D7=GS1.2.1729845643.4.1.1729846726.0.0.0")
                .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "none")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        JokeResponse res = om.readValue(response.body().string(), JokeResponse.class);
        return res.getValue();
    }

//    static class JokeResponse{
//        List<JokeObject>  result;
//
//        public List<JokeObject> getResult() {
//            return result;
//        }
//    }

    static class JokeResponse {
        String value;

        public String getValue() {
            return value;
        }
    }

}
