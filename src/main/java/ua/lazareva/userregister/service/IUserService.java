package ua.lazareva.userregister.service;


import ua.lazareva.userregister.entity.User;

import java.util.List;

public interface IUserService {

    List<User> getAll();

    User getById(Integer userId);

    void add(User user);

    void edit(User user);

    void delete(Integer userId);
}
