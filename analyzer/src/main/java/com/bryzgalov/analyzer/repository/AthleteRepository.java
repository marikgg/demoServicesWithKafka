package com.bryzgalov.analyzer.repository;

import java.util.List;
import com.bryzgalov.analyzer.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
  @Query("select a from Athlete a join fetch a.person")
  List<Athlete> findAll();

  long countByBenchPressIsLessThanAndPerson_Gender_Id(Long benchPress, Long genderId);
}
