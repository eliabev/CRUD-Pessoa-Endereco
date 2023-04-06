package com.attornatus.person.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {

    private long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String cidade;
    private boolean ehPrincipal;
}
