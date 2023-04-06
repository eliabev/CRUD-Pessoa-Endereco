package com.attornatus.person.service;

import com.attornatus.person.dto.EnderecoDTO;
import com.attornatus.person.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ModelMapper modelMapper;

//    public List<EnderecoDTO> listarEnderecoPessoa()

}
