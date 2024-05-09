package ru.edem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.edem.essences.Book;
import ru.edem.essences.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM person",new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(full_name,year) VALUES(?,?)",
                person.getFullName(),
                person.getYear());
    }
    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE user_id=?",
                        new BeanPropertyRowMapper<>(Person.class),
                        new Object[]{id})
                .stream().findAny().orElse(null);
    }

    public void update(int id,Person person){
        jdbcTemplate.update("UPDATE Person SET full_name=?, year=? WHERE user_id=?",
                person.getFullName(),
                person.getYear(),
                id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM person WHERE user_id=?", id);
    }

    public List<Book> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT id,name,year,author FROM Book WHERE user_id = ?",
                new BeanPropertyRowMapper<>(Book.class),id);

    }
}
