package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final Gson gson = new Gson();
    private final URL resourceUrl;

    public ResourcesFileLoader(String fileName) {
        resourceUrl = getClass().getClassLoader().getResource(fileName);
    }

    @Override
    public List<Measurement> load() {
        List<Measurement> measurementList;
        try (InputStreamReader inputStreamReader = new InputStreamReader(resourceUrl.openStream())) {
            measurementList = gson.fromJson(inputStreamReader, new TypeToken<ArrayList<Measurement>>() {
            }.getType());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return measurementList;
    }

}
