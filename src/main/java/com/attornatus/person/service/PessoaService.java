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
        pessoaRepository.save(pessoa);
        PessoaDTO pessoaSalva = buscarPorId(pessoa.getId());
        return pessoaSalva;
    }

    public PessoaDTO atualizarPessoa(Pessoa pessoa) {
        if (pessoa != null && Long.valueOf(pessoa.getId()) == null) {
            throw new IllegalArgumentException("O ID deve ser informado");
        }

        // Apenas para assegurar que a pessoa existe
        // caso negativo, uma excecao é lancada
        buscarPorId(pessoa.getId());

        return salvar(pessoa);
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
