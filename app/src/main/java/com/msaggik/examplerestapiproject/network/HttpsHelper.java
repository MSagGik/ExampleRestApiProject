package com.msaggik.examplerestapiproject.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.msaggik.examplerestapiproject.model.Product;
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
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class HttpsHelper {
    private SharedPreferences settings; // поле настроек приложения
    private final String NAME_SETTING = "RestApiApp"; // константа названия настроек
    private final String CONNECTION_SETTING = "Connection"; // константа названия настройки URL соединения

    //private String urlServer = "https://dummyjson.com/";
    private String urlServer = "";
    private final String ADD_QUOTES = "quotes?skip=0&limit=100";
    private final String ADD_PRODUCTS = "products?skip=0&limit=100";
    private String request; // url для запросов на сервер

    // метод считывания url сервера из настроек приложения
    private String urlServerSharedPreferences (Context context) {
        settings = context.getSharedPreferences(NAME_SETTING, Context.MODE_PRIVATE);
        return settings.getString(CONNECTION_SETTING, "NoUrl");
    }

    // метод проверки соединения с сервером
    public int connectionCheck(Context context) {
        urlServer = urlServerSharedPreferences(context);
        if (urlServer.equals("https://dummyjson.com/")) {
        request = urlServer;
        int code = 0;
        try {
            URL url = new URL(request);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            code = connection.getResponseCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return code;
        } return 404;
    }

    // метод формирования списка сущностей продуктов
    public List<Product> serverDataProduct(Context context) {
        urlServer = urlServerSharedPreferences(context);
        if (urlServer.equals("https://dummyjson.com/")) {
            request = urlServer + ADD_PRODUCTS;
            JSONObject jsonObject = serverRequest(request);
            List<Product> productList = new ArrayList<>();

            try {
                JSONArray jsonArray = jsonObject.getJSONArray("products");
                for (int i = 0; i < jsonArray.length(); i++) {
                    productList.add(new Product(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getString("title"),
                            jsonArray.getJSONObject(i).getDouble("price"), jsonArray.getJSONObject(i).getString("description"),
                            jsonArray.getJSONObject(i).getString("thumbnail"), jsonArray.getJSONObject(i).getJSONArray("images")
                            .getString(new Random().nextInt(jsonArray.getJSONObject(i).getJSONArray("images").length()))));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return productList;
        } return new ArrayList<>();
    }

    // метод формирования списка сущностей цитат
    public List<Quote> serverDataQuote(Context context) {
        urlServer = urlServerSharedPreferences(context);
        if (urlServer.equals("https://dummyjson.com/")) {
        request = urlServer + ADD_QUOTES;
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
        } return new ArrayList<>();
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