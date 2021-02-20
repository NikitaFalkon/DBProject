package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Controller
public class MainController {
    @Autowired
    private PersonRepository personRepository;
    @GetMapping("/hello")
    public String Hello(Model model)
    {
        List<Person> people = (List<Person>) personRepository.findAll();
        model.addAttribute("people", people);
        return "hello";
    }
    @GetMapping("/add")
    public String Add(Model model)
    {
        model.addAttribute("person", new Person());
        return "add";
    }
    @PostMapping("/adding")
    public String Adding(@RequestParam(name = "name", required = false) String name,
                         @RequestParam(name = "email", required = false) String email,
                         Model model)
    {
        Person person = new Person(name, email);
        personRepository.save(person);
        return "redirect:hello";
    }
    @GetMapping("/dell")
    public String Dell(Model model)
    {
        model.addAttribute("person", new Person());
        return "dell";
    }
    @PostMapping("/deleting")
    public String Deleting(@RequestParam(name = "id") long id)
    {

        Person person = personRepository.findById(id).orElseThrow();
        personRepository.delete(person);
        return "redirect:hello";
    }
    @GetMapping("/edit/{id}/person")
    public String Redact(@PathVariable(name = "id") long id, Model model)
    {
        if (!personRepository.existsById(id))
        {
            return "redirect:hello";
        }
        Optional<Person> person = personRepository.findById(id);
        ArrayList<Person> pers = new ArrayList<>();
        person.ifPresent(pers::add);
        model.addAttribute("people", pers);
        return "redact";
    }
    @PostMapping("/edit/{id}/person")
    public String Redacting(@PathVariable(name = "id") long id,
                            @RequestParam(name = "name", required = false) String name,
                         @RequestParam(name = "email", required = false) String email,
                         Model model)
    {
        Person person = personRepository.findById(id).orElseThrow();
        person.setEmail(email);
        person.setName(name);
        personRepository.save(person);
        return "redirect:/hello";
    }
}

