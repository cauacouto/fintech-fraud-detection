package com.example.account.service.config;

import com.account.service.TransferEvent;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProduceKafkaConfigTransfer {


    @Value("${spring.kafka.bootstrap-servers}")
    private String bootsTrapServes;

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistry;

    @Bean()
    public ProducerFactory<String, TransferEvent> producerFactory(){
        Map<String,Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootsTrapServes);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        //serializar o valor com json automaticamente
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,KafkaAvroSerializer.class);
        configProps.put("schema.registry.url",schemaRegistry);



        return new DefaultKafkaProducerFactory<>(configProps);

    }

    @Bean("transferKafkaTemplate")
    public KafkaTemplate<String,TransferEvent> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
