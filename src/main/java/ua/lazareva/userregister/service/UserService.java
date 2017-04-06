package ua.lazareva.userregister.service;

import ua.lazareva.userregister.dao.IGenericDao;
import ua.lazareva.userregister.entity.User;

import java.util.List;

public class UserService implements IUserService {
    private IGenericDao<User, Integer> userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    public User getById(Integer userId) {
        return userDao.getById(userId);
    }

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public void edit(User user) {
        userDao.edit(user);
    }

    @Override
    public void delete(Integer userId) {
        userDao.delete(userId);
    }


    public void setUserDao(IGenericDao<User, Integer> userDao) {
        this.userDao = userDao;
    }
}
