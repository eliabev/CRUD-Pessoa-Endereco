package com.attornatus.person.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MensagemRespostaException implements Serializable {

    private Date horario;
    private String mensagem;
    private String detalhes;
    private int statusCode;
}
