package com.attornatus.person.dto;

import com.attornatus.person.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PessoaDTO {

    private long id;
    private String nome;
    private Calendar dataNascimento;
    private List<Endereco> endereco;
}
