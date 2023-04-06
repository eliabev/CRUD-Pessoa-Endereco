package com.attornatus.person.controller;

import com.attornatus.person.dto.PessoaDTO;
import com.attornatus.person.entity.Pessoa;
import com.attornatus.person.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaDTO> salvar(@RequestBody Pessoa pessoa){
        PessoaDTO pessoaSalva = pessoaService.salvar(pessoa);
        return new ResponseEntity<PessoaDTO>(pessoaSalva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarPessoas(){
        List<PessoaDTO> listaDePessoas = pessoaService.listarPessoas();
        return ResponseEntity.ok(listaDePessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable("id") long id){
        PessoaDTO pessoaEncontrada = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(pessoaEncontrada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removerPorId(@PathVariable long id){
        pessoaService.removerPorId(id);
    }

    @PutMapping
    public ResponseEntity<PessoaDTO> atualizarPessoa(@RequestBody Pessoa pessoa){
        PessoaDTO pessoaSalva = pessoaService.atualizarPessoa(pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }
}
