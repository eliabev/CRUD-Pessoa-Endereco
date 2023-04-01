package com.attornatus.person.service;

import com.attornatus.person.entity.Pessoa;
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

    public List<Pessoa> listarPessoas(){
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> buscarPorId(long id){
        return pessoaRepository.findById(id);
    }

    public void removerPorId(long id){
        pessoaRepository.deleteById(id);
    }
}
