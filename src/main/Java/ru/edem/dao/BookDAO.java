package ru.edem.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.edem.essences.Book;
import ru.edem.essences.Person;


import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM book",new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(name,year,author) VALUES(?,?,?)",
                book.getName(),
                book.getYear(),
                book.getAuthor());
    }
    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?",
                        new BeanPropertyRowMapper<>(Book.class),
                        new Object[]{id})
                .stream().findAny().orElse(null);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id=?",
                id);
    }

    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE book SET name=?, year=?, author=? WHERE id=?",
                book.getName(),
                book.getYear(),
                book.getAuthor(),
                id);
    }
    public void addUserId(int id, Person person){
        jdbcTemplate.update("UPDATE book SET user_id=? WHERE id=?",
                person.getUser_id(),
                id);
    }
    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET user_id=NULL WHERE id=?", id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.user_id = Person.user_id " +
                        "WHERE Book.id = ?", new BeanPropertyRowMapper<>(Person.class),new Object[]{id})
                .stream().findAny();
    }
}
