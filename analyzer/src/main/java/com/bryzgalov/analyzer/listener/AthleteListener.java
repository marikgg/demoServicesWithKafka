package com.bryzgalov.analyzer.listener;


import com.bryzgalov.analyzer.dto.AthleteDto;
import com.bryzgalov.analyzer.service.AnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class AthleteListener {

  @Autowired
  private AnalyzerService analyzerService;


  @KafkaListener(topics = "${kafka.topic.request}", containerFactory = "requestListenerContainerFactory")
  @SendTo()
  public String receive(AthleteDto athleteDto, Acknowledgment acknowledgment) {
    String result;
    result = analyzerService.analyzeBenchPress(athleteDto);
    acknowledgment.acknowledge();
    return result;
  }
}
