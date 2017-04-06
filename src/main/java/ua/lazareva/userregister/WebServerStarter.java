package ua.lazareva.userregister;

import org.apache.commons.dbcp2.BasicDataSource;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ua.lazareva.userregister.dao.jdbc.JdbcUserDao;
import ua.lazareva.userregister.service.UserService;
import ua.lazareva.userregister.web.servlet.AddUserServlet;
import ua.lazareva.userregister.web.servlet.EditUserServlet;
import ua.lazareva.userregister.web.servlet.GetAllUsersServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class WebServerStarter {


    public static void main(String[] args) {

        // dao
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/web");
        dataSource.setUsername("web_app_user");
        dataSource.setPassword("webapp");
        dataSource.setInitialSize(5);
        JdbcUserDao jdbc = new JdbcUserDao();
        jdbc.setDataSource(dataSource);
        UserService service = new UserService();
        service.setUserDao(jdbc);

        // servlet
        GetAllUsersServlet getAllServlet = new GetAllUsersServlet();
        AddUserServlet addServlet = new AddUserServlet();
        EditUserServlet editServlet =new EditUserServlet();
        getAllServlet.setUserService(service);
        addServlet.setUserService(service);
        editServlet.setUserService(service);

        // web config
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(getAllServlet), "/index.html");
        handler.addServlet(new ServletHolder(addServlet), "/add.html");
        handler.addServlet(new ServletHolder(editServlet), "/edit.html");

        // resources
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{"/index.html"});

        resource_handler.setResourceBase("src/main/webapp/");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, handler});

        Server server = new Server(8080);
        server.setHandler(handlers);
        try {
            server.start();
            System.out.println("Web server is started");
        } catch (Exception e) {
            System.out.println("Start of web server is failed");
            e.printStackTrace();
        }
    }
}
