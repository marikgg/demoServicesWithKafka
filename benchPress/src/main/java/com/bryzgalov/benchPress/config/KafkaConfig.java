package com.bryzgalov.benchPress.config;

import java.util.HashMap;
import java.util.Map;
import com.bryzgalov.benchPress.dto.AthleteDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

  @Value("${io.reflectoring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${kafka.consumer.group.id}")
  private String groupId;

  @Value("${kafka.topic.reply}")
  private String replyTopic;


  @Bean
  public Map < String, Object > consumerConfigs() {
    Map < String, Object > props = new HashMap < > ();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

    return props;
  }


  @Bean
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = new HashMap<>();

    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
      bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      JsonSerializer.class);
    return props;
  }

  @Bean
  public ProducerFactory < String, AthleteDto > requestProducerFactory() {
    return new DefaultKafkaProducerFactory < > (producerConfigs());
  }

  @Bean
  public ConsumerFactory< String, String > replyConsumerFactory() {
    return new DefaultKafkaConsumerFactory< >(consumerConfigs(), new StringDeserializer(),
      new StringDeserializer());
  }

  @Bean
  public KafkaMessageListenerContainer < String, String > replyListenerContainer() {
    ContainerProperties containerProperties = new ContainerProperties(replyTopic);
    return new KafkaMessageListenerContainer < > (replyConsumerFactory(), containerProperties);
  }



  @Bean
  public ReplyingKafkaTemplate < String, AthleteDto, String > replyKafkaTemplate(
    ProducerFactory <String, AthleteDto> pf,
    KafkaMessageListenerContainer<String, String> lc) {
    return new ReplyingKafkaTemplate < > (pf, lc);
  }

}
