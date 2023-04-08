package com.attornatus.person.controller;

import com.attornatus.person.dto.EnderecoDTO;
import com.attornatus.person.entity.Endereco;
import com.attornatus.person.service.EnderecoService;
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
@RequestMapping("/endereco")
@Api(value = "/endereco", tags = "Endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @ApiOperation(value = "Cria um novo endereco para a pessoa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "\"id\": x\n\"cep\": cep\n\"logradouro\": logradouro\n\"numero\": xx\n\"cidade\": cidade\n\"ehPrincipal\": t/f"),
            @ApiResponse(code = 404, message = "Não foi encontrada a pessoa com esse ID :("),
            @ApiResponse(code = 404, message = "Não existe um endereco com esse ID :(")
    })
    @PostMapping
    public ResponseEntity<EnderecoDTO> salvarEndereco(@RequestBody Endereco endereco) {
        EnderecoDTO enderecoSalvo = enderecoService.salvarEndereco(endereco);
        return new ResponseEntity<EnderecoDTO>(enderecoSalvo, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista enderecos de uma pessoa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "\"id\": x\n\"cep\": cep\n\"logradouro\": logradouro\n\"numero\": xx\n\"cidade\": cidade\n\"ehPrincipal\": t/f"),
            @ApiResponse(code = 404, message = "Não existe nenhum endereço para esse id :(")
    })
    @GetMapping("/{id}")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecoPessoa(@PathVariable long id){
        List<EnderecoDTO> enderecosEncontrados = enderecoService.listarEnderecoPessoa(id);

        return ResponseEntity.ok(enderecosEncontrados);
    }

    @ApiOperation(value = "Encontra o endereco principal de uma pessoa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "\"id\": x\n\"cep\": cep\n\"logradouro\": logradouro\n\"numero\": xx\n\"cidade\": cidade\n\"ehPrincipal\": t"),
            @ApiResponse(code = 404, message = "Não existe um endereco principal para essa pessoa")
    })
    @GetMapping("principal/{idPessoa}")
    public ResponseEntity<EnderecoDTO> encontrarEnderecoPrincipal(@PathVariable long idPessoa) {
        EnderecoDTO enderecoPrincipal = enderecoService.encontrarEnderecoPrincipal(idPessoa);

        return ResponseEntity.ok(enderecoPrincipal);
    }

    @ApiOperation(value = "Encontra o endereco principal de uma pessoa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "\"id\": x\n\"cep\": cep\n\"logradouro\": logradouro\n\"numero\": xx\n\"cidade\": cidade\n\"ehPrincipal\": t"),
            @ApiResponse(code = 404, message = "Não existe um endereco com esse ID :(")
    })
    @PutMapping("principal/{idPessoa}/{idEndereco}")
    public ResponseEntity<EnderecoDTO> alterarEnderecoPrincipal(@PathVariable long idPessoa, @PathVariable long idEndereco) {
        EnderecoDTO enderecoPrincipal = enderecoService.alterarEnderecoPrincipal(idPessoa, idEndereco);

        return ResponseEntity.ok(enderecoPrincipal);
    }
}
