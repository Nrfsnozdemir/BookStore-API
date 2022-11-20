package com.bookStore.pages;

import com.bookStore.utilities.BookStoreApiUtils;
import com.bookStore.utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponseApiPage {

    public Map<String, String> getRequestBody() {
        String requestUserName = ConfigurationReader.getProperty("userName");
        String requestPassword = ConfigurationReader.getProperty("passWord");
        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("userName", requestUserName);
        requestBody.put("password", requestPassword);
        return requestBody;
    }

    public Map<String, Object> addBookRequestBody(String isbn) {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        List<Map<String, String>> collectionsOfIsbn = new ArrayList<>();
        Map<String, String> isbnMap = new LinkedHashMap<>();
        isbnMap.put("isbn", isbn);
        collectionsOfIsbn.add(isbnMap);
        requestBody.put("userId", BookStoreApiUtils.readFromFile());
        requestBody.put("collectionOfIsbns", collectionsOfIsbn);
        return requestBody;
    }
}
