package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;

public class ClientApiServlet extends HttpServlet {
    private final DbServiceClientImpl dbServiceClient;


    public ClientApiServlet(TransactionManagerHibernate transactionManager) {
        DataTemplateHibernate<Client> clientTemplate = new DataTemplateHibernate<>(Client.class);
        dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.err.println(req.getRequestURI());
        System.err.println(req.getPathInfo());
        System.err.println(req.getPathInfo().substring(1));
        System.err.println(req.getPathInfo().substring(1).length());
        String name = req.getPathInfo().substring(1);
        if (name.length() > 0) {
            dbServiceClient.saveClient(new Client(name));
        }
    }
}
