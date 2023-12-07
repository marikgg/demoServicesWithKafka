package com.bryzgalov.analyzer.resource;

import java.util.List;
import com.bryzgalov.analyzer.entity.Person;
import com.bryzgalov.analyzer.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
public class PersonResource {
  @Autowired
  private PersonService personService;


  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Person> getAll() {
    return personService.findAll();
  }
}
