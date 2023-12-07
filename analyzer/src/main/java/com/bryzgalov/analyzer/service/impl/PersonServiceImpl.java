package com.bryzgalov.analyzer.service.impl;

import java.util.List;
import com.bryzgalov.analyzer.entity.Person;
import com.bryzgalov.analyzer.repository.PersonRepository;
import com.bryzgalov.analyzer.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Override
  public List<Person> findAll() {
    return personRepository.findAllPersons();
  }
}
