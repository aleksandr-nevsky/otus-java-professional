package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;

import java.io.IOException;
import java.util.stream.Collectors;

public class ClientApiServlet extends HttpServlet {
    private final DbServiceClientImpl dbServiceClient;
    private final Gson gson;

    public ClientApiServlet(Gson gson, DbServiceClientImpl dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String clientJson = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Client newClient = gson.fromJson(clientJson, Client.class);
        dbServiceClient.saveClient(newClient);
    }
}
