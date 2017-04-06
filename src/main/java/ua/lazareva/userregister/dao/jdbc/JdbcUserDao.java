package ua.lazareva.userregister.dao.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import ua.lazareva.userregister.dao.IGenericDao;
import ua.lazareva.userregister.dao.jdbc.mapper.Mapper;
import ua.lazareva.userregister.entity.User;

import java.sql.*;
import java.util.List;

public class JdbcUserDao implements IGenericDao<User, Integer> {

    private static final String prepareSelectAll = "select user_id, first_name, last_name, birth_date, salary from web.users";
    private static final String prepareSelectById = "select user_id, first_name, last_name, birth_date, salary from web.users where user_id = ?";
    private static final String prepareInsert = "insert into web.users(first_name, last_name, birth_date, salary) values(?,?,?,?)";
    private static final String prepareUpdate = "update web.users set first_name = ?, last_name = ?, birth_date = ?, salary = ? where user_id = ?";
    private static final String prepareDelete = "delete from web.users where user_id = ?";

    private BasicDataSource dataSource;

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getAll() {

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(prepareSelectAll);
            ResultSet resultSet = statement.executeQuery();
            return Mapper.mapToUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getById(Integer userId) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(prepareSelectById);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return Mapper.mapToUser(resultSet).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(User user) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(prepareInsert);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            if (user.getDateOfBirth() == null) {
                statement.setNull(3, Types.DATE);
            } else {
                statement.setDate(3, Date.valueOf(user.getDateOfBirth()));
            }
            if (user.getSalary() == null) {
                statement.setNull(4, Types.FLOAT);
            } else {
                statement.setFloat(4, user.getSalary());
            }
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit(User user) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(prepareUpdate);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            if (user.getDateOfBirth() == null) {
                statement.setNull(3, Types.DATE);
            } else {
                statement.setDate(3, Date.valueOf(user.getDateOfBirth()));
            }
            if (user.getSalary() == null) {
                statement.setNull(4, Types.FLOAT);
            } else {
                statement.setFloat(4, user.getSalary());
            }
            statement.setInt(5, user.getUserId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Integer userId) {

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(prepareDelete);
            statement.setInt(1, userId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
