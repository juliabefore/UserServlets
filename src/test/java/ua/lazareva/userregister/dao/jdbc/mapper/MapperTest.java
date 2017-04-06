package ua.lazareva.userregister.dao.jdbc.mapper;

import org.junit.jupiter.api.Test;
import ua.lazareva.userregister.entity.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.lazareva.userregister.web.utility.dto.DateFormatter.dateFormat;


class MapperTest {

    @Test
    void testMapToUser() throws SQLException {
        List<User> expectedList = new ArrayList<>();
        expectedList.add(new User(-1, "Olga", "Lazareva", LocalDate.parse("1982/11/04", DateTimeFormatter.ofPattern(dateFormat)), 99.99f));
        expectedList.add(new User(-2, "Nastya", "Lazareva", LocalDate.parse("2009/06/04", DateTimeFormatter.ofPattern(dateFormat)), 100.01f));

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("user_id")).thenReturn(-1).thenReturn(-2);
        when(resultSet.getString("first_name")).thenReturn("Olga").thenReturn("Nastya");
        when(resultSet.getString("last_name")).thenReturn("Lazareva").thenReturn("Lazareva");
        when(resultSet.getDate("birth_date")).thenReturn(Date.valueOf(LocalDate.parse("1982/11/04", DateTimeFormatter.ofPattern(dateFormat)))).thenReturn(Date.valueOf(LocalDate.parse("2009/06/04", DateTimeFormatter.ofPattern(dateFormat))));
        when(resultSet.getFloat("salary")).thenReturn(99.99f).thenReturn(100.01f);
        List<User> actualList = new Mapper().mapToUser(resultSet);

        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
    }
}
