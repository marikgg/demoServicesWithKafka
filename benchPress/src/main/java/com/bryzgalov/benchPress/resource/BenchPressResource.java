package com.bryzgalov.benchPress.resource;

import com.bryzgalov.benchPress.dto.AthleteDto;
import com.bryzgalov.benchPress.service.BenchPressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analyze")
public class BenchPressResource {

  @Autowired
  private BenchPressService benchPressService;


  @PostMapping(value = "/benchPress")
  public String checkBenchPress(@RequestBody AthleteDto athleteDto) {
    return benchPressService.checkBenchPress(athleteDto);
  }

}
