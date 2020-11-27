package br.coop.cf.torcedores.service;

import br.coop.cf.torcedores.model.Torcedor;
import br.coop.cf.torcedores.util.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class KafkaService {

    private final ConfigProperties configProperties;
    private final KafkaTemplate<String, Torcedor> kafkaTemplate;

    KafkaService(ConfigProperties configProperties, KafkaTemplate<String, Torcedor> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
        this.configProperties = configProperties;
    }

    public void sendMessage(Torcedor torcedor) {

        ListenableFuture<SendResult< String, Torcedor >> future = kafkaTemplate.send( configProperties.getTopicTorcedorCadastrado(), torcedor );
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Torcedor> result) {
                log.info( "Sent message=[" + torcedor + "] with offset=[" + result.getRecordMetadata().offset() + "]" );
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error( "Unable to send message=[" + torcedor + "] due to : " + ex.getMessage(), ex );
            }
        });
    }

    @KafkaListener(topics = "TorcedorCadastrado", groupId = "torcedor-app")
    public void listenGroupFoo(@Payload Torcedor torcedor, @Headers MessageHeaders headers ) {
        System.out.println("Received Message in group foo: " + torcedor );
    }


}
