package com.attornatus.person.service;

import com.attornatus.person.dto.PessoaDTO;
import com.attornatus.person.entity.Pessoa;
import com.attornatus.person.exceptions.EntidadeNaoEncontradaException;
import com.attornatus.person.exceptions.PessoaInvalidaException;
import com.attornatus.person.repository.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PessoaDTO salvar(Pessoa pessoa){
        validarPayload(pessoa);

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);;
        return modelMapper.map(pessoaSalva, PessoaDTO.class);
    }

    public PessoaDTO atualizarPessoa(Pessoa pessoaAtualizada) {
        if (pessoaAtualizada != null && pessoaAtualizada.getId() == null) {
            throw new PessoaInvalidaException("O ID deve ser informado");
        }

        PessoaDTO pessoaAtual = buscarPorId(pessoaAtualizada.getId());
        // removendo valores nulos
        modelMapper.map(pessoaAtualizada, pessoaAtual);

        Pessoa entidadePessoa = modelMapper.map(pessoaAtual, Pessoa.class);
        return salvar(entidadePessoa);
    }

    public List<PessoaDTO> listarPessoas(){
        return pessoaRepository.findAll().stream()
                .map((pessoa) -> modelMapper.map(pessoa, PessoaDTO.class))
                .collect(Collectors.toList());
    }

    public PessoaDTO buscarPorId(Long id){
        return pessoaRepository.findById(id)
                .map((pessoa) -> modelMapper.map(pessoa, PessoaDTO.class))
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi encontrada a pessoa com esse ID :("));
    }

    public void removerPorId(Long id){
        buscarPorId(id);
        pessoaRepository.deleteById(id);
    }

    public void validarPayload(Pessoa pessoa) {
        if (!Objects.nonNull(pessoa)
                || !StringUtils.hasText(pessoa.getNome())
                || !Objects.nonNull(pessoa.getDataNascimento())) {
            throw new PessoaInvalidaException("Nome e Data de nascimento precisam ser informados!");
        }
    }
}
