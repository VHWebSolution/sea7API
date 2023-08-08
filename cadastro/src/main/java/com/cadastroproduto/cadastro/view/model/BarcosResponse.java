package com.cadastroproduto.cadastro.view.model;

import java.util.List;

import com.cadastroproduto.cadastro.model.entity.barcos.ImageModelDTO;
import com.cadastroproduto.cadastro.model.entity.barcos.ImagemSecundariaDTO;

public class BarcosResponse {
    private String id;

    private String barcoNome;

    private double barcoPreco;

    private String barcoDescricaoCurta;

    private String barcoDescricaoCompleta;

    private List<ImageModelDTO> barcoImagens;
    
    private List<ImagemSecundariaDTO> ImagemSecundaria;

    public List<ImagemSecundariaDTO> getImagemSecundaria() {
        return ImagemSecundaria;
    }

    public void setImagemSecundaria(List<ImagemSecundariaDTO> imagemSecundaria) {
        ImagemSecundaria = imagemSecundaria;
    }

    public String getBarcoNome() {
        return barcoNome;
    }

    public void setBarcoNome(String barcoNome) {
        this.barcoNome = barcoNome;
    }

    public double getBarcoPreco() {
        return barcoPreco;
    }

    public void setBarcoPreco(double barcoPreco) {
        this.barcoPreco = barcoPreco;
    }

    public String getBarcoDescricaoCurta() {
        return barcoDescricaoCurta;
    }

    public void setBarcoDescricaoCurta(String barcoDescricaoCurta) {
        this.barcoDescricaoCurta = barcoDescricaoCurta;
    }

    public String getBarcoDescricaoCompleta() {
        return barcoDescricaoCompleta;
    }

    public void setBarcoDescricaoCompleta(String barcoDescricaoCompleta) {
        this.barcoDescricaoCompleta = barcoDescricaoCompleta;
    }

    public List<ImageModelDTO> getBarcoImagens() {
        return barcoImagens;
    }

    public void setBarcoImagens(List<ImageModelDTO> barcoImagens) {
        this.barcoImagens = barcoImagens;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
