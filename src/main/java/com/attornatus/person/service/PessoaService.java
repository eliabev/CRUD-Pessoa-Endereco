package com.attornatus.person.service;

import com.attornatus.person.entity.Pessoa;
import com.attornatus.person.exceptions.EntidadeNaoEncontradaException;
import com.attornatus.person.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa salvar(Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizarPessoa(Pessoa pessoa) {
        if (pessoa != null && Long.valueOf(pessoa.getId()) == null) {
            throw new IllegalArgumentException("O ID deve ser informado");
        }

        buscarPorId(pessoa.getId());

        return salvar(pessoa);
    }

    public List<Pessoa> listarPessoas(){
        return pessoaRepository.findAll();
    }

    public Pessoa buscarPorId(long id){
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        if(pessoaOptional.isEmpty()) throw new EntidadeNaoEncontradaException("Não foi encontrada a pessoa com esse ID :(");

        return pessoaOptional.get();
    }

    public void removerPorId(long id){
        pessoaRepository.deleteById(id);
    }
}
