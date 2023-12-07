package com.bryzgalov.analyzer.repository;

import java.util.List;
import com.bryzgalov.analyzer.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
  @Query("select p from Person p join fetch p.gender")
  List<Person> findAllPersons();
}
