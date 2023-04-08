package com.attornatus.person.controller;

import com.attornatus.person.dto.PessoaDTO;
import com.attornatus.person.entity.Pessoa;
import com.attornatus.person.service.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
@Api(value = "/pessoa", tags = "Pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;


    @ApiOperation(value = "Cria uma nova pessoa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "\"id\": x\n\"nome\": nome\n\"dataNascimento\": YYYY-MM-DD\n\"enderecos\": []"),
            @ApiResponse(code = 400, message = "Nome e Data de nascimento precisam ser informados!")
    })
    @PostMapping
    public ResponseEntity<PessoaDTO> salvar(@RequestBody Pessoa pessoa){
        PessoaDTO pessoaSalva = pessoaService.salvar(pessoa);
        return new ResponseEntity<PessoaDTO>(pessoaSalva, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista todas as pessoas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array com todas as pessoas, ou array vazio")
    })
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarPessoas(){
        List<PessoaDTO> listaDePessoas = pessoaService.listarPessoas();
        return ResponseEntity.ok(listaDePessoas);
    }

    @ApiOperation(value = "Encontra pessoa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "\"id\": x\n\"nome\": nome\n\"dataNascimento\": YYYY-MM-DD\n\"enderecos\": []"),
            @ApiResponse(code = 404, message = "Não foi encontrada a pessoa com esse ID :(")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable("id") long id){
        PessoaDTO pessoaEncontrada = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(pessoaEncontrada);
    }

    @ApiOperation(value = "Deleta pessoa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "Não foi encontrada a pessoa com esse ID :(")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removerPorId(@PathVariable long id){
        pessoaService.removerPorId(id);
    }

    @ApiOperation(value = "Atualiza pessoa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "\"id\": x\n\"nome\": nome\n\"dataNascimento\": YYYY-MM-DD\n\"enderecos\": []"),
            @ApiResponse(code = 404, message = "Não foi encontrada a pessoa com esse ID :(")
    })
    @PutMapping
    public ResponseEntity<PessoaDTO> atualizarPessoa(@RequestBody Pessoa pessoa){
        PessoaDTO pessoaSalva = pessoaService.atualizarPessoa(pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }
}
