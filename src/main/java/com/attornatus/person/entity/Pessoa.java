package com.attornatus.person.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;

import lombok.*;

import java.util.Calendar;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Calendar dataNascimento;

    @JsonManagedReference
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Endereco> enderecos;
}
