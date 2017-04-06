package ua.lazareva.userregister.dao.jdbc.mapper;

import ua.lazareva.userregister.entity.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static List<User> mapToUser(ResultSet resultSet) {

        List<User> list = new ArrayList<>();
        try {
            while (resultSet.next()) {

                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                Date dateOfBirth = resultSet.getDate("birth_date");
                if (dateOfBirth != null) {
                    user.setDateOfBirth(dateOfBirth.toLocalDate());
                }
                user.setSalary(resultSet.getFloat("salary"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception in processing of resultSet" + "\n" + e);
        }
        return list;
    }
}
