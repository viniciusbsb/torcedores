package br.coop.cf.torcedores;

import br.coop.cf.torcedores.constants.TipoTelefone;
import br.coop.cf.torcedores.exceptions.TorcedorNotFoundException;
import br.coop.cf.torcedores.model.Endereco;
import br.coop.cf.torcedores.model.Telefone;
import br.coop.cf.torcedores.model.Torcedor;
import br.coop.cf.torcedores.repository.TorcedorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TorcedoresApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TorcedorRepository torcedorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String CPF = "00203152018";

    @Test
    @Order(1)
    void findCepTest() throws Exception {

        mockMvc.perform(get(String.format("http://localhost:%s/enderecos/integracao/%s", port, "71929360"))
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:admin".getBytes()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void saveTorcedorTest() throws Exception {

        var endereco = Endereco.builder()
                .cep("71929360")
                .complemento("complemento")
                .localidade("localidade")
                .logradouro("logradouro")
                .numero("numero")
                .uf("uf")
                .bairro("bairro")
                .build();

        var telefones = Stream.of(
                Telefone.builder()
                        .telefone("61999999999")
                        .tipoTelefone(TipoTelefone.CELULAR)
                        .isPrincipal(true)
                        .build(),
                Telefone.builder()
                        .telefone("6199999999")
                        .tipoTelefone(TipoTelefone.RESIDENCIAL
                        )
                        .isPrincipal(false)
                        .build()
        ).collect(Collectors.toSet());

        var torcedor = Torcedor.builder()
                .cpf(CPF)
                .nome("fulano")
                .email("fulano@email.com")
                .endereco(endereco)
                .telefones( telefones )
                .build();

        mockMvc.perform(post(String.format("http://localhost:%s/torcedores", port))
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:admin".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString( torcedor ) ) )
                .andExpect(status().isOk());

        var torcedorSalvo = torcedorRepository.findTorcedorByCpf(CPF).orElseThrow( TorcedorNotFoundException::new );
        assertThat( torcedorSalvo != null && torcedorSalvo.getId() != null );
    }

    @Test
    @Order(3)
    void cpfConstraintErrorTest() throws Exception {

        var torcedorSalvo = torcedorRepository.findTorcedorByCpf(CPF).orElseThrow( TorcedorNotFoundException::new );
        torcedorSalvo.setId( null );
        torcedorSalvo.getEndereco().setId( null );
        torcedorSalvo.getTelefones().forEach( t -> t.setId( null ) );

        mockMvc.perform(post(String.format("http://localhost:%s/torcedores", port))
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:admin".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString( torcedorSalvo ) ) )
                .andExpect(status().is4xxClientError());

    }

    @Test
    @Order(4)
    void updateTorcedorTest() throws Exception {

        var torcedor = torcedorRepository.findTorcedorByCpf(CPF).orElseThrow( TorcedorNotFoundException::new );
        torcedor.setEmail( "email@alterado.com" );

        mockMvc.perform(put(String.format("http://localhost:%s/torcedores/%s", port, torcedor.getId()))
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:admin".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString( torcedor ) ) )
                .andExpect(status().isOk());

        var torcedorAtualizado = torcedorRepository.findTorcedorByCpf( CPF ).orElseThrow( TorcedorNotFoundException::new );
        assertThat( torcedor.getEmail().equals( torcedorAtualizado.getEmail() ) );
    }

    @Test
    @Order(5)
    void findAllTorcedorTest() throws Exception {

        var torcedor = torcedorRepository.findTorcedorByCpf( CPF ).orElseThrow( TorcedorNotFoundException::new );
        mockMvc.perform(get(String.format("http://localhost:%s/torcedores/search", port))
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:admin".getBytes()))
                .param( "cpf", CPF )
                .param( "nome", "" ) )
                .andExpect(status().isOk())
                .andExpect( content().string( containsString( objectMapper.writeValueAsString( torcedor ) )  ) );
    }

    @Test
    @Order(6)
    void deleteTorcedorTest() throws Exception {

        var torcedor = torcedorRepository.findTorcedorByCpf( CPF ).orElseThrow( TorcedorNotFoundException::new );
        mockMvc.perform(delete(String.format("http://localhost:%s/torcedores/%s", port, torcedor.getId()))
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:admin".getBytes())))
                .andExpect(status().is2xxSuccessful());
    }

}
