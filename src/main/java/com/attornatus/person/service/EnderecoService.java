package com.attornatus.person.service;

import com.attornatus.person.dto.EnderecoDTO;
import com.attornatus.person.entity.Endereco;
import com.attornatus.person.exceptions.EntidadeNaoEncontradaException;
import com.attornatus.person.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<EnderecoDTO> listarEnderecoPessoa(long pessoaId) {
        List<EnderecoDTO> enderecos = enderecoRepository.findByPessoaId(pessoaId).stream()
                .map((endereco) -> modelMapper.map(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());

        if(enderecos.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não existe nenhum endereço para esse id.");
        }

        return enderecos;
    }

//    public EnderecoDTO salvarEndereco(Endereco endereco){
//
//    }

}
