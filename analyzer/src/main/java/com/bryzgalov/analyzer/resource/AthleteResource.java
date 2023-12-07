package com.bryzgalov.analyzer.resource;

import java.util.List;
import com.bryzgalov.analyzer.dto.AthleteDto;
import com.bryzgalov.analyzer.entity.Athlete;
import com.bryzgalov.analyzer.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/athlete")
public class AthleteResource {

  @Autowired
  private AthleteService athleteService;


  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Athlete> getAll() {
    return athleteService.findAll();
  }

  @PostMapping(value = "/saveOne")
  public void saveOne(@RequestBody AthleteDto athleteDto) {
    athleteService.save(athleteDto);
  }
}
