package com.attornatus.person.service;

import com.attornatus.person.dto.PessoaDTO;
import com.attornatus.person.entity.Pessoa;
import com.attornatus.person.exceptions.EntidadeNaoEncontradaException;
import com.attornatus.person.repository.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PessoaDTO salvar(Pessoa pessoa){
        if(pessoa.getNome().isBlank() || pessoa.getDataNascimento() == null) {
            throw new IllegalArgumentException("Por favor, informe os parâmetros: Nome e Data de Nascimento");
        }
        pessoaRepository.save(pessoa);
        PessoaDTO pessoaSalva = buscarPorId(pessoa.getId());
        return pessoaSalva;
    }

    public PessoaDTO atualizarPessoa(Pessoa pessoaAtualizada) {
        if (pessoaAtualizada != null && Long.valueOf(pessoaAtualizada.getId()) == null) {
            throw new IllegalArgumentException("O ID deve ser informado");
        }

        PessoaDTO pessoaAtual = buscarPorId(pessoaAtualizada.getId());
        // removendo valores nulos
        modelMapper.map(pessoaAtualizada, pessoaAtual);

        Pessoa entidadePessoa = modelMapper.map(pessoaAtual, Pessoa.class);
        return salvar(entidadePessoa);
    }

    public List<PessoaDTO> listarPessoas(){
        return pessoaRepository.findAll().stream()
                .map((pessoa) -> modelMapper.map(pessoa, PessoaDTO.class))
                .collect(Collectors.toList());
    }

    public PessoaDTO buscarPorId(long id){
        return pessoaRepository.findById(id)
                .map((pessoa) -> modelMapper.map(pessoa, PessoaDTO.class))
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi encontrada a pessoa com esse ID :("));
    }

    public void removerPorId(long id){
        buscarPorId(id);
        pessoaRepository.deleteById(id);
    }
}
