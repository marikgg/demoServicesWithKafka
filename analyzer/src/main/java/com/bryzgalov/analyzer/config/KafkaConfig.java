package com.bryzgalov.analyzer.config;

import java.util.HashMap;
import java.util.Map;
import com.bryzgalov.analyzer.dto.AthleteDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConfig {

  @Value("${io.reflectoring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${kafka.consumer.group.id}")
  private String groupId;

  @Bean
  public Map< String, Object> consumerConfigs() {
    Map <String, Object> props = new HashMap<>();

    JsonDeserializer<AthleteDto> deserializer = new JsonDeserializer<>(AthleteDto.class);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

    return props;
  }

  @Bean
  public Map < String, Object > producerConfigs() {
    Map < String, Object > props = new HashMap < > ();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

    return props;
  }

  @Bean
  public ConsumerFactory< String, AthleteDto > requestConsumerFactory() {
    JsonDeserializer<AthleteDto> deserializer = new JsonDeserializer<>(AthleteDto.class);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);

    return new DefaultKafkaConsumerFactory< >(consumerConfigs(), new StringDeserializer(),
      deserializer);
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer< String, AthleteDto >> requestListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory < String, AthleteDto > factory =
      new ConcurrentKafkaListenerContainerFactory< >();
    factory.setConsumerFactory(requestConsumerFactory());
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    factory.getContainerProperties().setSyncCommits(true);;
    factory.setReplyTemplate(replyTemplate());
    return factory;
  }

  @Bean
  public ProducerFactory< String, String > replyProducerFactory() {
    return new DefaultKafkaProducerFactory< >(producerConfigs());
  }

  @Bean
  public KafkaTemplate<String, String> replyTemplate() {
    KafkaTemplate<String, String> template = new KafkaTemplate <> (replyProducerFactory());
    return template;
  }
}
