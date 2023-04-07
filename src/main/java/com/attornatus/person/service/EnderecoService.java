package com.attornatus.person.service;

import com.attornatus.person.dto.EnderecoDTO;
import com.attornatus.person.entity.Endereco;
import com.attornatus.person.entity.Pessoa;
import com.attornatus.person.exceptions.EnderecoInvalidoException;
import com.attornatus.person.exceptions.EntidadeNaoEncontradaException;
import com.attornatus.person.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ModelMapper modelMapper;

    public List<EnderecoDTO> listarEnderecoPessoa(long pessoaId) {
        List<Endereco> enderecos = enderecoRepository.findByPessoaId(pessoaId);

        if(enderecos.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não existe nenhum endereço para esse id.");
        }

        return enderecos.stream()
                .map((endereco) -> modelMapper.map(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public EnderecoDTO salvarEndereco(Endereco endereco){
        validarPayload(endereco);
        Pessoa donoDoEndereco = endereco.getPessoa();
        pessoaService.validarPayload(donoDoEndereco);
        pessoaService.buscarPorId(donoDoEndereco.getId());

        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        return modelMapper.map(enderecoSalvo, EnderecoDTO.class);
    }

    public void validarPayload(Endereco endereco) {
        if(!Objects.nonNull(endereco)
        || endereco.getCep().isBlank()
        || endereco.getCidade().isBlank()
        || endereco.getLogradouro().isBlank()
        || !Objects.nonNull(endereco.getPessoa())){
            throw new EnderecoInvalidoException("Os atributos CEP, cidade, logradouro e \"Pessoa\" devem ser informados!");
        }
    }

}
