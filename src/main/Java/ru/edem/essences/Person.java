package ru.edem.essences;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Component
public class Person {
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @NotEmpty(message = "Поле не должно быть пустым!")
    @Pattern(regexp = "[А-я]\\w+ [А-я]\\w+ [А-я]\\w+", message = "Неверный формат. Пример: Иванов, Иван, Иванович")
    private String fullName;
    @Min(value = 1900, message = "Введите корректный год!")
    private int year;

    public Person() {
    }
    public Person(int id,String fullName, int year) {
        this.user_id = id;
        this.fullName = fullName;
        this.year = year;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
