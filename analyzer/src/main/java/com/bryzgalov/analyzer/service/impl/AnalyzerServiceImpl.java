package com.bryzgalov.analyzer.service.impl;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import com.bryzgalov.analyzer.dto.AthleteDto;
import com.bryzgalov.analyzer.entity.Gender;
import com.bryzgalov.analyzer.repository.GenderRepository;
import com.bryzgalov.analyzer.service.AnalyzerService;
import com.bryzgalov.analyzer.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerServiceImpl implements AnalyzerService {

  @Autowired
  private AthleteService athleteService;

  @Autowired
  private GenderRepository genderRepository;

  @Override
  public String analyzeBenchPress(AthleteDto athleteDto) {
    String result;

    Optional<Gender> optGender = genderRepository.findGenderByName(athleteDto.getGender());
    if (optGender.isPresent()) {
      Gender gender = optGender.get();
      long superiorityPercentage = athleteService.findSuperiorityPercentage(gender.getId(), athleteDto.getBenchPress());
      result = "Your bench press better then " + superiorityPercentage + "% of " + gender.getName();
      CompletableFuture.runAsync(() -> athleteService.save(athleteDto));
    } else {
      result = "Not found such gender";
    }

    return result;
  }
}
