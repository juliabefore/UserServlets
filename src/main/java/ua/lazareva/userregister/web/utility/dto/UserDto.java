package ua.lazareva.userregister.web.utility.dto;

import ua.lazareva.userregister.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static ua.lazareva.userregister.web.utility.dto.DateFormatter.dateFormat;
public class UserDto {

    private int userId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Float salary;

    public UserDto(User user) {
        userId = user.getUserId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        dateOfBirth = convertDate(user.getDateOfBirth());
        salary = user.getSalary();
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Float getSalary() {
        return salary;
    }

    private String convertDate(LocalDate date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            return date.format(formatter);
        }
        return null;
    }
}