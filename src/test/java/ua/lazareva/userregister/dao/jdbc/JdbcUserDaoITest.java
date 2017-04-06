package ua.lazareva.userregister.dao.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.lazareva.userregister.entity.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ua.lazareva.userregister.web.utility.dto.DateFormatter.dateFormat;

class JdbcUserDaoITest {
    JdbcUserDao jdbcDao = new JdbcUserDao();
    BasicDataSource dataSource;

    @BeforeAll
    void setDatasource() {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/web");
        dataSource.setUsername("web_app_user");
        dataSource.setPassword("webapp");
        dataSource.setInitialSize(5);
        jdbcDao.setDataSource(dataSource);
    }

    @Test
    void testAdd() {
        jdbcDao.add(new User(-1, "Test", "Test User", LocalDate.parse("1982/11/04", DateTimeFormatter.ofPattern(dateFormat)), 99.99f));
    }

    @Test
    void testGetAll() {
        List<User> list = jdbcDao.getAll();
        for (User user : list) {
            assertNotNull(user.getUserId());
            assertNotNull(user.getFirstName());
            assertNotNull(user.getLastName());
            System.out.println(user);
        }
    }

    @Test
    void edit() {
        jdbcDao.edit(new User(-1, "Test [updated]", "Test User [updated]", LocalDate.parse("1982/11/30", DateTimeFormatter.ofPattern(dateFormat)), 100.01f));
    }

    @Test
    void testGetById() {
        User user = jdbcDao.getById(-1);
        assertEquals(-1, user.getUserId());
        assertEquals("Test [updated]", user.getFirstName());
        assertEquals("Test User [updated]", user.getLastName());
        assertEquals(LocalDate.parse("1982/11/30", DateTimeFormatter.ofPattern(dateFormat)), user.getDateOfBirth());
        assertEquals((Float) 100.01f, user.getSalary());
        System.out.println(user);
    }


    @Test
    void delete() {
        jdbcDao.delete(-1);
        User user = jdbcDao.getById(-1);
    }

    @AfterAll
    void closeDatasource() throws SQLException {
        dataSource.close();

    }

}