package com.attornatus.person.controller;

import com.attornatus.person.dto.PessoaDTO;
import com.attornatus.person.entity.Endereco;
import com.attornatus.person.entity.Pessoa;
import com.attornatus.person.exceptions.EntidadeNaoEncontradaException;
import com.attornatus.person.exceptions.PessoaInvalidaException;
import com.attornatus.person.service.PessoaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTests {

    @MockBean
    private PessoaService pessoaService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PessoaController pessoaController;

    Calendar calendar = Calendar.getInstance();
    Date date = new Date();
    Pessoa pessoa1;
    Pessoa pessoa2;
    Pessoa pessoa3;
    PessoaDTO pessoaDto1;
    PessoaDTO pessoaDto2;
    PessoaDTO pessoaDto3;
    List<Pessoa> pessoas;
    List<PessoaDTO> pessoasDto;
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String pessoaJson;
    private final String enderecoBase = "http://localhost:8080/pessoa";

    @BeforeEach
    public void setup() throws JsonProcessingException {
        calendar.setTime(date);
        pessoa1 = new Pessoa(1L, "Edvair Silveira", calendar, new ArrayList<Endereco>());
        pessoa2 = new Pessoa(2L, "Romulo Andrade", calendar, new ArrayList<Endereco>());
        pessoa3 = new Pessoa(3L, "Bianca Nogueira", calendar, new ArrayList<Endereco>());
        pessoaDto1 = new PessoaDTO(1L, "Edvair Silveira", calendar, new ArrayList<Endereco>());
        pessoaDto2 = new PessoaDTO(2L, "Romulo Andrade", calendar, new ArrayList<Endereco>());
        pessoaDto3 = new PessoaDTO(3L, "Bianca Nogueira", calendar, new ArrayList<Endereco>());
        pessoas = new ArrayList<>(Arrays.asList(pessoa1, pessoa2, pessoa3));
        pessoasDto = new ArrayList<>(Arrays.asList(pessoaDto1, pessoaDto2, pessoaDto3));
        pessoaJson = ow.writeValueAsString(pessoa1);
    }

    @Test
    @DisplayName("listarPessoas deve retornar status OK")
    void listarPessoasDeveRetornarStatusOk() throws Exception {
        this.mockMvc.perform(get(enderecoBase)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("buscarPorId deve retornar JSON contendo a pessoa")
    void buscarPorIdDeveRetornarJsonContendoAPessoa() throws Exception {
        String stringEsperada = "{\"id\":1,\"nome\":\"Edvair Silveira\"";
        when(pessoaService.buscarPorId(1L)).thenReturn(pessoaDto1);

        MvcResult result = this.mockMvc.perform(get(enderecoBase + "/1"))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8)
                                        .contains(stringEsperada));
    }

    @Test
    @DisplayName("buscarPorId deve retornar status 204 quando a pessoa não for encontrada")
    void buscarPorIdDeveRetornarStatus404QuandoPessoaNaoForEncontrada() throws Exception {
        when(pessoaService.buscarPorId(4L)).thenThrow(new EntidadeNaoEncontradaException("Não foi encontrada a pessoa com esse ID :("));

        MvcResult result = this.mockMvc.perform(get(enderecoBase + "/4"))
                .andExpect(status().isNoContent()).andReturn();

        assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8)
                .contains("Não foi encontrada a pessoa com esse ID :("));
    }

    @Test
    @DisplayName("salvarPessoa deve retornar a pessoa salva")
    void salvarPessoaDeveRetornarAPessoaSalva() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String pessoaJson = ow.writeValueAsString(pessoa1);
        String stringEsperada = "{\"id\":1,\"nome\":\"Edvair Silveira\"";
        when(pessoaService.salvarPessoa(any())).thenReturn(pessoaDto1);

        MvcResult result = this.mockMvc.perform(post(enderecoBase)
                        .contentType(MediaType.APPLICATION_JSON).content(pessoaJson))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8)
                .contains(stringEsperada));
    }

    @Test
    @DisplayName("salvarPessoa deve retornar Bad Request se PessoaInvalidaException for lançada")
    void salvarPessoaDeveRetornarBadRequestSePessoaInvalidaExceptionForLancada() throws Exception {

        String stringEsperada = "Nome e Data de nascimento precisam ser informados!";
        when(pessoaService.salvarPessoa(any())).thenThrow(new PessoaInvalidaException("Nome e Data de nascimento precisam ser informados!"));

        MvcResult result = this.mockMvc.perform(post(enderecoBase)
                        .contentType(MediaType.APPLICATION_JSON).content(pessoaJson))
                .andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8)
                .contains(stringEsperada));
    }

    @Test
    @DisplayName("removerPorId deve retornar status OK")
    void removerPorIdDeveRetornarStatusOk() throws Exception {
        doNothing().when(pessoaService).removerPorId(1L);
        this.mockMvc.perform(delete(enderecoBase + "/1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("atualizarPessoa deve retornar a pessoa atualizada")
    void atualizarPessoaDeveRetornarAPessoaAtualizada() throws Exception {
        when(pessoaService.atualizarPessoa(any())).thenReturn(pessoaDto1);
        this.mockMvc.perform(put(enderecoBase)
                .contentType(MediaType.APPLICATION_JSON).content(pessoaJson)
        ).andExpect(status().isOk());
    }
}
