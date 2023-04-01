package com.attornatus.person.controller;

import com.attornatus.person.entity.Pessoa;
import com.attornatus.person.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    @ResponseStatus
    public Pessoa salvar(Pessoa pessoa){
        return pessoaService.salvar(pessoa);
    }
}
