package com.cadastroproduto.cadastro.model.entity.barcos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "imagem_secundaria")
@Entity(name = "imagem_secundaria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idImgSec")
public class ImagemSecundaria {
     @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idImgSec;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @Column(length = 50000000)
    @NotNull
    private byte[] picByte;

    public ImagemSecundaria(String name, String type, byte[] picByte) {
        this.name = name;
        this.picByte = picByte;
        this.type = type;
    }
}
