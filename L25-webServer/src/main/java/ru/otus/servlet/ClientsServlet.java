package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.model.ClientTest;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_RANDOM_USER = "randomUser";

    private final TemplateProcessor templateProcessor;
    private final Gson gson;

    public ClientsServlet(TemplateProcessor templateProcessor, Gson gson) {
        this.templateProcessor = templateProcessor;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        List<ClientTest> clients = new ArrayList<>();
        clients.add(new ClientTest("name", "phone"));
        clients.add(new ClientTest("name2", "phone2"));
//        response.setContentType("application/json;charset=UTF-8");
//        ServletOutputStream out = response.getOutputStream();
//        out.print(gson.toJson(clients));


        Map<String, Object> paramsMap = new HashMap<>();
//        userDao.findRandomUser().ifPresent(randomUser -> paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, randomUser));
        paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, new ClientTest("name", "phone"));
        paramsMap.put("clients", clients);


        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
//        response.getWriter().println(templateProcessor.getPage("users.html", paramsMap));
//        response.getWriter().println("hello");
    }

}
