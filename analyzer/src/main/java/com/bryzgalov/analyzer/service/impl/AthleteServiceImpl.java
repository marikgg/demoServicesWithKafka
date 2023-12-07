package com.bryzgalov.analyzer.service.impl;

import java.util.List;
import com.bryzgalov.analyzer.dto.AthleteDto;
import com.bryzgalov.analyzer.entity.Athlete;
import com.bryzgalov.analyzer.entity.Gender;
import com.bryzgalov.analyzer.entity.Person;
import com.bryzgalov.analyzer.repository.AthleteRepository;
import com.bryzgalov.analyzer.repository.GenderRepository;
import com.bryzgalov.analyzer.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AthleteServiceImpl implements AthleteService {

  @Autowired
  private AthleteRepository athleteRepository;

  @Autowired
  private GenderRepository genderRepository;

  @Override
  public List<Athlete> findAll() {
    return athleteRepository.findAll();
  }

  @Override
  public long findSuperiorityPercentage(Long genderId, Long benchPress) {
    long athleteCount = athleteRepository.countByBenchPressIsLessThanAndPerson_Gender_Id(benchPress, genderId);
    long totalCount = athleteRepository.count();
    return (long) (((double) athleteCount/totalCount) * 100);
  }

  @Override
  @Transactional
  public void save(AthleteDto athleteDto) {
    Gender gender = genderRepository.findGenderByName(athleteDto.getGender()).orElseThrow();

    Person person = new Person(athleteDto.getName(), gender);
    Athlete athlete = new Athlete(person, athleteDto.getBenchPress());
    athleteRepository.save(athlete);
  }
}
