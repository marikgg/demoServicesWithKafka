package com.bryzgalov.analyzer.repository;

import java.util.Optional;
import com.bryzgalov.analyzer.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenderRepository extends JpaRepository<Gender, Long> {
  @Query("select g from Gender g where lower(g.name) = lower(:name)")
  Optional<Gender> findGenderByName(String name);
}
