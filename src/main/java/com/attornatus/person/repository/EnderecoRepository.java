package com.attornatus.person.repository;

import com.attornatus.person.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    public List<Endereco> findByPessoaId(Long pessoaId);
}
