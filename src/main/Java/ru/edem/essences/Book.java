package ru.edem.essences;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Component
public class Book {

    private int id;
    @NotEmpty(message = "Поле не должно быть пустым!")
    private String name;
    @Min(value = 0,message = "Неверный год!")
    private int year;
    @NotEmpty(message = "Поле не должно быть пустым!")
//    @Pattern(regexp = "[А-я]\\w+, [А-я]\\w+", message = "Неверный формат. Пример: Иванов, Иван")
    private String author;

    public Book() {
    }
    public Book(int id, String name, int year, String author) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.author = author;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
