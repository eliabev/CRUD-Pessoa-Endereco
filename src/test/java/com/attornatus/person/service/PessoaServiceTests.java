package com.attornatus.person.service;

import com.attornatus.person.dto.PessoaDTO;
import com.attornatus.person.entity.Endereco;
import com.attornatus.person.entity.Pessoa;
import com.attornatus.person.exceptions.EntidadeNaoEncontradaException;
import com.attornatus.person.exceptions.PessoaInvalidaException;
import com.attornatus.person.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTests {

    @Mock
    private PessoaRepository pessoaRepository;
    @Spy
    private ModelMapper modelMapper = new ModelMapper();
    @InjectMocks
    private PessoaService pessoaService;

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

    @BeforeEach
    public void setup() {
        calendar.setTime(date);
        pessoa1 = new Pessoa(1L, "Edvair Silveira", calendar, new ArrayList<Endereco>());
        pessoa2 = new Pessoa(2L, "Romulo Andrade", calendar, new ArrayList<Endereco>());
        pessoa3 = new Pessoa(3L, "Bianca Nogueira", calendar, new ArrayList<Endereco>());
        pessoaDto1 = new PessoaDTO(1L, "Edvair Silveira", calendar, new ArrayList<Endereco>());
        pessoaDto2 = new PessoaDTO(2L, "Romulo Andrade", calendar, new ArrayList<Endereco>());
        pessoaDto3 = new PessoaDTO(3L, "Bianca Nogueira", calendar, new ArrayList<Endereco>());
        pessoas = new ArrayList<>(Arrays.asList(pessoa1, pessoa2, pessoa3));
        pessoasDto = new ArrayList<>(Arrays.asList(pessoaDto1, pessoaDto2, pessoaDto3));
    }

    @Test
    @DisplayName("Deve salvar uma pessoa quando os parâmetros corretos forem passados")
    public void deveSalvarUmaPessoaQuandoOsParametrosForemPassados() {
        when(pessoaRepository.save(pessoa1)).thenReturn(pessoa1);
        PessoaDTO pessoaRetornada = pessoaService.salvarPessoa(pessoa1);
        assertEquals(pessoaDto1, pessoaRetornada);
    }

    @Test
    @DisplayName("Deve retornar uma lista com todas as pessoas")
    public void deveRetornarUmaListaComTodasAsPessoasDto() {
        when(pessoaRepository.findAll()).thenReturn(pessoas);
        List<PessoaDTO> pessoasRetornadas = pessoaService.listarPessoas();
        assertArrayEquals(pessoasDto.toArray(), pessoasRetornadas.toArray());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando validarPayload for chamado com um valor nulo")
    public void deveLancarUmaExcecaoQuandoValidarPayloadForChamadoComValorNulo() {
        pessoa1 = null;
        assertThrows(PessoaInvalidaException.class, () -> pessoaService.validarPayload(pessoa1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", ""})
    @DisplayName("Deve lançar uma exceção quando validarPayload for chamado com uma pessoa sem nome")
    public void deveLancarUmaExcecaoQuandoValidarPayloadForChamadoComUmaPessoaSemNome(String nome) {
        pessoa1.setNome(nome);
        assertThrows(PessoaInvalidaException.class, () -> pessoaService.validarPayload(pessoa1));
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando atualizarPessoa for chamado com um id nulo")
    public void deveLancarUmaExcecaoQuandoAtualizarPessoaForChamadoComIdNulo() {
        pessoa1.setId(null);
        assertThrows(PessoaInvalidaException.class, () -> pessoaService.atualizarPessoa(pessoa1));
    }

    @Test
    @DisplayName("Deve substituir a pessoa existente quando atualizarPessoa for chamado")
    public void deveSubstituirAPessoaExistenteQuandoAtualizarPessoaForChamado() {
        when(pessoaRepository.save(pessoa1)).thenReturn(pessoa1);
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa1));
        pessoaService.salvarPessoa(pessoa1);
        pessoa1.setNome("Bianca Lira");
        PessoaDTO pessoaAtualizada = pessoaService.atualizarPessoa(pessoa1);
        assertEquals("Bianca Lira", pessoaAtualizada.getNome());
        assertEquals(1L, pessoaAtualizada.getId());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando buscarPorId for chamado com um ID desconhecido")
    public void deveLancarUmaExcecaoQuandoBuscarPorIdForChamadoComIdDesconhecido() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        assertThrows(EntidadeNaoEncontradaException.class, () -> pessoaService.buscarPorId(1L));
    }

    @Test
    @DisplayName("Deve retornar uma PessoaDTO quando buscarPorId for chamado com ID conhecido")
    public void deveRetornarPessoaDtoQuandoBuscarPorIdForChamadoComIdConhecido() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa1));
        assertEquals(pessoaDto1, pessoaService.buscarPorId(1L));
    }

    @Test
    @DisplayName("Deve remover a pessoa quando removerPorId for chamado com ID conhecido")
    public void deveRemoverPessoaQuandoRemoverPorIdForChamadoComIdConhecido() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa1));
        pessoaService.removerPorId(1L);
        Mockito.verify(pessoaRepository, times(1)).deleteById(1L);
    }
}
