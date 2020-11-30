package br.coop.cf.torcedores.service;

import br.coop.cf.torcedores.dto.TorcedorDTO;
import br.coop.cf.torcedores.model.Torcedor;
import br.coop.cf.torcedores.util.ConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;

    KafkaService( ConfigProperties configProperties,
                  KafkaTemplate<String, String> kafkaTemplate,
                  ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.configProperties = configProperties;
        this.objectMapper = objectMapper;
    }

    public void sendMessageCadastro(Torcedor torcedor) {

        try {
            var msg = TorcedorDTO.builder().cpf(torcedor.getCpf()).nome(torcedor.getNome()).build();

            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(configProperties.getTopicTorcedorCadastrado(), objectMapper.writeValueAsString(msg));
            handleKafkaMessage(torcedor, future);
        }catch( JsonProcessingException e ) {

            log.error( e.getMessage(), e );
        }
    }

    public void sendMessageDesligado(Torcedor torcedor) {
        try {
            var msg = TorcedorDTO.builder().cpf(torcedor.getCpf()).nome(torcedor.getNome()).build();

            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(configProperties.getTopicTorcedorDesligado(), objectMapper.writeValueAsString(msg));
            handleKafkaMessage(torcedor, future);
        }catch( JsonProcessingException e ) {

            log.error( e.getMessage(), e );
        }
    }

    private void handleKafkaMessage(Torcedor torcedor, ListenableFuture<SendResult<String, String>> future) {
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info(String.format("Mensagem enviado com sucesso [%s]", torcedor.getCpf()));
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(String.format("Falha ao enviar mensagem [%s]", torcedor.getCpf()), ex);
            }
        });
    }

}
