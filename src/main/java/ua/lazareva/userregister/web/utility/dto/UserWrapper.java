package ua.lazareva.userregister.web.utility.dto;

import ua.lazareva.userregister.entity.User;

import java.util.ArrayList;
import java.util.List;


public class UserWrapper {

    public static List<UserDto> wrap(List<User> users) {
        List<UserDto> wrappedList = new ArrayList<>();
        for (User user : users) {
            wrappedList.add(new UserDto(user));
        }
        return wrappedList;
    }

    public static UserDto wrap(User user) {
        return new UserDto(user);
    }
}
