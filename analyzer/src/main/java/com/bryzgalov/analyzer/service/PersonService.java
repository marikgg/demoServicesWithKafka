package com.bryzgalov.analyzer.service;

import java.util.List;
import com.bryzgalov.analyzer.entity.Person;

public interface PersonService {
  List<Person> findAll();
}
