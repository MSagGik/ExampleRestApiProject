package com.msaggik.examplerestapiproject.network;

import android.os.Handler;

import com.msaggik.examplerestapiproject.model.Quote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpsHelper {

    private final String URL_SERVER = "https://dummyjson.com/";
    private final String ADD_QUOTES = "quotes?skip=0&limit=100";
    private String request; // url для запросов на сервер

    // метод формирования списка сущностей цитат
    public List<Quote> serverDataQuote() {
        request = URL_SERVER + ADD_QUOTES;
        JSONObject jsonObject = serverRequest(request);
        List<Quote> quoteList = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("quotes");
            for(int i = 0; i < jsonArray.length(); i++) {
                quoteList.add(new Quote(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getString("quote"), jsonArray.getJSONObject(i).getString("author")));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return quoteList;
    }
    
    // метод получения JSON объекта с сервера по запросу
    private JSONObject serverRequest(String request) {
        JSONObject json = null;
        try {
            URL url = new URL(request); // создание url ссылки для запроса на сервер
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection(); // открытие соединения с сервером
            connection.connect(); // соединение с сервером

            InputStream stream = connection.getInputStream(); // Считывание данных из потока ответа сервера
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream)); // Запись данных и выделение памяти

            StringBuffer buffer = new StringBuffer(); // Запись данных и выделение памяти
            String line = ""; // По умолчанию пустая строка

            while ((line = reader.readLine()) != null) // Постройное считывание текста
                buffer.append(line).append("\n");
            String response = buffer.toString(); // текстовый ответ с сервера

            json = new JSONObject(response); // создание JSON объекта по ответу с сервера

        } catch (MalformedURLException e) { // исключение на случай отсутствия ссылки request
            e.printStackTrace();
        } catch (IOException e) { // исключение на случай отсутствия соединения
            e.printStackTrace();
        } catch (JSONException e) { // исключение отсутствия JSON объекта
            e.printStackTrace();
        }
        return json;
    }
}
