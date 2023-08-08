package com.cadastroproduto.cadastro.model.entity.mensagem;

import com.cadastroproduto.cadastro.model.entity.barcos.Barcos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="idMsg")
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idMsg;
    @NotEmpty
    @NotBlank
    @NotNull
    private String nomePessoa;
    @NotEmpty
    @NotBlank
    @NotNull
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{4}-\\d{4}", message = "Telefone inválido. O formato deve ser (XX) XXXX-XXXX.")
    private String telefonePessoa;
    @NotEmpty
    @NotBlank
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String mensagemPessoa;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Barcos barcos;

}
