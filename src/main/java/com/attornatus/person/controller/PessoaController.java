package com.attornatus.person.controller;

import ch.qos.logback.core.net.server.Client;
import com.attornatus.person.entity.Pessoa;
import com.attornatus.person.service.PessoaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa salvar(Pessoa pessoa){
        return pessoaService.salvar(pessoa);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Pessoa> listarPessoas(){
        return pessoaService.listarPessoas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pessoa buscarPorId(@PathVariable("id") long id){
        return pessoaService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPessoa(@PathVariable long id){
         pessoaService.removerPorId(id);
    }

    @PutMapping
    public Pessoa atualizarPessoa(Pessoa pessoa){
        return pessoaService.atualizarPessoa(pessoa);
    }
}
