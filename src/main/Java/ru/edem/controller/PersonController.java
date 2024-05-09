package ru.edem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.edem.dao.BookDAO;
import ru.edem.dao.PersonDAO;
import ru.edem.essences.Book;
import ru.edem.essences.Person;
import ru.edem.validator.PersonValidator;

import java.util.Optional;


@Controller
@RequestMapping("/person")
public class PersonController {
    private PersonDAO personDAO;
    private BookDAO bookDAO;
    private PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("user",personDAO.index());
        return "person/index";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("person") Person person){
        return "person/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/person";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person",personDAO.show(id));
        model.addAttribute("books",personDAO.getBookOwner(id));
        return "person/show";
    }

    @DeleteMapping("/{id}")
    public String del(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/person";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return "person/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        personDAO.update(id,person);
        return "redirect:/person";
    }
}
