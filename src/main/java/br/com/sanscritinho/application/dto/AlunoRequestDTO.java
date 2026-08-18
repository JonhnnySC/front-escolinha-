package br.com.sanscritinho.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlunoRequestDTO implements Serializable {

    private String nome;
    private String matricula;
    private Double nota;
    private String observacoes;
}
