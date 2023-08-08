package com.cadastroproduto.cadastro.model.entity.barcos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Table(name = "barcos")
@Entity(name = "barcos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Barcos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotNull
    @NotBlank
    private String barcoNome;
    @NotNull
    private double barcoPreco;
    @NotNull
    private String barcoDescricaoCurta;
    @Column(columnDefinition = "TEXT")
    @NotNull
    private String barcoDescricaoCompleta;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "barco_imagem", joinColumns = {
            @JoinColumn(name = "barco_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "imagem_id")
    })
    private Set<ImageModel> barcoImagem;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "barco_imagem_secundaria", joinColumns = {
            @JoinColumn(name = "barco_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "imagem_secundaria_id")
    })
    private Set<ImagemSecundaria> imagemSecundaria;

}
