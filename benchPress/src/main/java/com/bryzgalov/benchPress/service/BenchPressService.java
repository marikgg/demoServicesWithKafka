package com.bryzgalov.benchPress.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.bryzgalov.benchPress.dto.AthleteDto;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

@Service
public class BenchPressService {

  @Value("${kafka.topic.request}")
  private String requestTopic;

  @Value("${kafka.topic.reply}")
  private String replyTopic;

  @Autowired
  private ReplyingKafkaTemplate< String, AthleteDto, String > requestReplyKafkaTemplate;

  @SneakyThrows
  public String checkBenchPress(AthleteDto athleteDto)  {
    ProducerRecord<String, AthleteDto> record = new ProducerRecord<>(requestTopic, athleteDto);
    record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes()));
    RequestReplyFuture<String, AthleteDto, String> sendAndReceive = requestReplyKafkaTemplate.sendAndReceive(record, Duration.ofSeconds(10));
    return sendAndReceive.get(10, TimeUnit.SECONDS).value();
  }
}
