package ua.lazareva.userregister.web.servlet;

import ua.lazareva.userregister.entity.User;
import ua.lazareva.userregister.service.IUserService;
import ua.lazareva.userregister.web.utility.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

public class AddUserServlet extends HttpServlet {
    private IUserService userService;
    private PageGenerator page = PageGenerator.instance();

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = page.parseRequestParam(request);

        String responsePage = page.getPage("add.html", pageVariables);
        response.getWriter().println(responsePage);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> parameters = request.getParameterMap();

        if (page.getPostedValue(parameters, "firstName").equals("") ||
                page.getPostedValue(parameters, "lastName").equals("")) {
            throw new RuntimeException("First and Last names are obligatory to filling out!!!");
        }
        User user = new User();
        user.setFirstName(page.getPostedValue(parameters, "firstName"));
        user.setLastName(page.getPostedValue(parameters, "lastName"));
        LocalDate date = page.getPostedValue(parameters, "dateOfBirth").isEmpty() ? null : LocalDate.parse(page.getPostedValue(parameters, "dateOfBirth"), ofPattern("dd/MM/yyyy"));
        user.setDateOfBirth(date);
        Float salary = page.getPostedValue(parameters, "salary").isEmpty() ? null : Float.parseFloat(page.getPostedValue(parameters, "salary"));
        user.setSalary(salary);

        userService.add(user);

        response.sendRedirect("index.html");
    }
}

