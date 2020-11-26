package br.coop.cf.torcedores.service;

import br.coop.cf.torcedores.util.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class KafkaService {

    private final ConfigProperties configProperties;
    private final KafkaTemplate<String, String> kafkaTemplate;

    KafkaService(ConfigProperties configProperties, KafkaTemplate<String, String> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
        this.configProperties = configProperties;
    }

    public void sendMessage( String message ) {

        ListenableFuture<SendResult< String, String >> future = kafkaTemplate.send( configProperties.getTopicTorcedorCadastrado(), message );
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info( "Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]" );
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error( "Unable to send message=[" + message + "] due to : " + ex.getMessage(), ex );
            }
        });
    }

    @KafkaListener(topics = "TorcedorCadastrado", groupId = "TorcedorCadastrado")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
    }


}
