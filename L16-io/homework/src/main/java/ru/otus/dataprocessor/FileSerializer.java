package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;
    private final Gson gson = new Gson();

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        String json = gson.toJson(data);
        try {
            Files.writeString(Paths.get(fileName), json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
