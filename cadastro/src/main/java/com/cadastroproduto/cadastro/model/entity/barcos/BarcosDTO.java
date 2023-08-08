package com.cadastroproduto.cadastro.model.entity.barcos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BarcosDTO {
    
    private String id;
    @NotNull
    @NotBlank
    private String barcoNome;
    @NotNull
    private double barcoPreco;
    @NotNull
    private String barcoDescricaoCurta;
    @NotNull
    private String barcoDescricaoCompleta;
    
    private List<ImageModelDTO> barcoImagens;

    private List<ImagemSecundariaDTO> ImagemSecundaria;

  

   

   
}
