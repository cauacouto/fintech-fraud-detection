package com.example.account.service.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import tools.jackson.databind.ser.jdk.StringSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProduceKafkaConfig {


    @Value("${spring.kafka.bootstrap-servers}")
    private String bootsTrapServes;

    @Bean
    public ProducerFactory<String,Object> producerFactory(){
        Map<String,Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootsTrapServes);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        //serializar o valor com json automaticamente
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);

        //iclui o tipo java no header da mensagem (ajuda o consumer a desserializar)
        configProps.put(JacksonJsonSerializer.ADD_TYPE_INFO_HEADERS,false);

        return new DefaultKafkaProducerFactory<>(configProps);

    }

    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
