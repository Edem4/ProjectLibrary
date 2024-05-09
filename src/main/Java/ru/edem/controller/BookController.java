package ru.edem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.edem.dao.BookDAO;
import ru.edem.dao.PersonDAO;
import ru.edem.essences.Book;
import ru.edem.essences.Person;

import java.util.Optional;


@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("book",bookDAO.index());
        return "book/indexBook";
    }
    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book){
        return "book/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book",bookDAO.show(id));
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());

        return "book/show";
    }


    @PostMapping
    public String create(@ModelAttribute("book") Book book, BindingResult bindingResult){
        bookDAO.save(book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String del(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.show(id));
        return "book/edit";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book){
        bookDAO.update(id, book);
        return "redirect:/book";
    }
    @PatchMapping("/{id}/assign")
    public String newUserId(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        bookDAO.addUserId(id,person);
        return "redirect:/book/" + id;
    }
    @PatchMapping("/{id}/release")
    public String delUserId(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/book/" + id;
    }
}
