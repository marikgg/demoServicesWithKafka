package com.bryzgalov.analyzer.service;

import java.util.List;
import com.bryzgalov.analyzer.dto.AthleteDto;
import com.bryzgalov.analyzer.entity.Athlete;
import org.springframework.transaction.annotation.Transactional;

public interface AthleteService {

  List<Athlete> findAll();

  long findSuperiorityPercentage(Long genderId, Long benchPress);

  @Transactional
  void save(AthleteDto athleteDto);

}
