package ua.lazareva.userregister.web.servlet;

import static ua.lazareva.userregister.web.utility.dto.DateFormatter.dateFormat;
import ua.lazareva.userregister.entity.User;
import ua.lazareva.userregister.service.IUserService;
import ua.lazareva.userregister.web.utility.PageGenerator;
import ua.lazareva.userregister.web.utility.dto.UserWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

public class EditUserServlet extends HttpServlet {

    private IUserService userService;
    private PageGenerator page = PageGenerator.instance();

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> requestParam = page.parseRequestParam(request);
        requestParam.put("user", UserWrapper.wrap(userService.getById(Integer.parseInt(page.getPostedValue(request.getParameterMap(), "userId")))));
        String responsePage = page.getPage("edit.html", requestParam);
        response.getWriter().println(responsePage);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> parameters = request.getParameterMap();
        String submitButton = page.getPostedValue(parameters, "button");
        if (submitButton.equals("save")) {

            User user = new User();
            user.setUserId(Integer.parseInt(page.getPostedValue(parameters, "userId")));
            user.setFirstName(page.getPostedValue(parameters, "firstName"));
            user.setLastName(page.getPostedValue(parameters, "lastName"));
            LocalDate date = page.getPostedValue(parameters, "dateOfBirth").isEmpty() ? null : LocalDate.parse(page.getPostedValue(parameters, "dateOfBirth"), ofPattern(dateFormat));
            user.setDateOfBirth(date);
            Float salary = page.getPostedValue(parameters, "salary").isEmpty() ? null : Float.parseFloat(page.getPostedValue(parameters, "salary"));
            user.setSalary(salary);

            userService.edit(user);

        } else if (submitButton.equals("delete")) {
            userService.delete(Integer.parseInt(page.getPostedValue(parameters, "userId")));
        }
        response.sendRedirect("index.html");

    }
}
