package br.coop.cf.torcedores.service;

import br.coop.cf.torcedores.model.Torcedor;
import br.coop.cf.torcedores.util.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class KafkaService {

    private final ConfigProperties configProperties;
    private final KafkaTemplate<String, Torcedor> kafkaTemplate;

    KafkaService(ConfigProperties configProperties, KafkaTemplate<String,  Torcedor> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
        this.configProperties = configProperties;
    }

    public void sendMessageCadastro(Torcedor torcedor) {

        ListenableFuture<SendResult< String, Torcedor >> future = kafkaTemplate.send( configProperties.getTopicTorcedorCadastrado(), torcedor );
        handleKafkaMessage(torcedor, future);
    }

    public void sendMessageDesligado(Torcedor torcedor) {

        ListenableFuture<SendResult< String, Torcedor >> future = kafkaTemplate.send( configProperties.getTopicTorcedorDesligado(), torcedor );
        handleKafkaMessage(torcedor, future);
    }

    private void handleKafkaMessage(Torcedor torcedor, ListenableFuture<SendResult<String, Torcedor>> future) {
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Torcedor> result) {
                log.info(String.format("Mensagem enviado com sucesso [%s]", torcedor.getCpf()));
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(String.format("Falha ao enviar mensagem [%s]", torcedor.getCpf()), ex );
            }
        });
    }


    @KafkaListener(topics = "TorcedorCadastrado", groupId = "torcedor-app")
    public void listenGroupFoo(@Payload Torcedor torcedor, @Headers MessageHeaders headers ) {
        System.out.printf("Mensagem recebida: %s%n", torcedor.getCpf());
    }


}
