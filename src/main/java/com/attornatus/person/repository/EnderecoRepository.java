package com.attornatus.person.repository;

import com.attornatus.person.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    @Query("SELECT e FROM Endereco e JOIN Pessoa p ON e.pessoa = p.id WHERE p.id = :pessoaId")
    public List<Endereco> findByPessoaId(long pessoaId);
}
