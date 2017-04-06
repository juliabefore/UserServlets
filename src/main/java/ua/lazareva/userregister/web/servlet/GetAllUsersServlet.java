package ua.lazareva.userregister.web.servlet;

import ua.lazareva.userregister.entity.User;
import ua.lazareva.userregister.service.IUserService;
import ua.lazareva.userregister.web.utility.PageGenerator;
import ua.lazareva.userregister.web.utility.dto.UserDto;
import ua.lazareva.userregister.web.utility.dto.UserWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetAllUsersServlet extends HttpServlet {
    private PageGenerator page = PageGenerator.instance();
    private IUserService userService;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = page.parseRequestParam(request);
        pageVariables.put("users", UserWrapper.wrap(userService.getAll()));

        String responsePage = page.getPage("index.html", pageVariables);
        response.getWriter().println(responsePage);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private List<UserDto> wrap(List<User> users) {
        List<UserDto> wrappedList = new ArrayList<>();
        for (User user : users) {
            wrappedList.add(new UserDto(user));
        }
        return wrappedList;
    }
}
