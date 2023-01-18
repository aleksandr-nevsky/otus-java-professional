package ru.otus;

import com.google.gson.Gson;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.server.ClientWebServer;
import ru.otus.server.ClientWebServerWithBasicSecurity;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;


public class WebServerWithBasicSecurityHw {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static DbServiceClientImpl dbServiceClient;
    private static TransactionManagerHibernate transactionManager;

    public static void main(String[] args) throws Exception {
        prepareDb();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);

        ClientWebServer usersWebServer = new ClientWebServerWithBasicSecurity(WEB_SERVER_PORT, loginService, templateProcessor,
                new Gson(), dbServiceClient);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static void prepareDb() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
        transactionManager = new TransactionManagerHibernate(sessionFactory);
        dbServiceClient = new DbServiceClientImpl(transactionManager, new DataTemplateHibernate<>(Client.class));
    }
}
