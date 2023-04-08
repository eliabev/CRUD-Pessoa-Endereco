package com.attornatus.person.controller;

import com.attornatus.person.dto.EnderecoDTO;
import com.attornatus.person.entity.Endereco;
import com.attornatus.person.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<EnderecoDTO> salvarEndereco(@RequestBody Endereco endereco) {
        EnderecoDTO enderecoSalvo = enderecoService.salvarEndereco(endereco);
        return new ResponseEntity<EnderecoDTO>(enderecoSalvo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecoPessoa(@PathVariable long id){
        List<EnderecoDTO> enderecosEncontrados = enderecoService.listarEnderecoPessoa(id);

        return ResponseEntity.ok(enderecosEncontrados);
    }

    @GetMapping("principal/{idPessoa}")
    public ResponseEntity<EnderecoDTO> encontrarEnderecoPrincipal(@PathVariable long idPessoa) {
        EnderecoDTO enderecoPrincipal = enderecoService.encontrarEnderecoPrincipal(idPessoa);

        return ResponseEntity.ok(enderecoPrincipal);
    }

    @PutMapping("principal/{idPessoa}/{idEndereco}")
    public ResponseEntity<EnderecoDTO> alterarEnderecoPrincipal(@PathVariable long idPessoa, @PathVariable long idEndereco) {
        EnderecoDTO enderecoPrincipal = enderecoService.alterarEnderecoPrincipal(idPessoa, idEndereco);

        return ResponseEntity.ok(enderecoPrincipal);
    }
}
