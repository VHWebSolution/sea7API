package com.cadastroproduto.cadastro.view.model;

import java.util.List;

import com.cadastroproduto.cadastro.model.entity.barcos.ImageModelDTO;

public class InfoBasicaResponse {
    private String id;

    private String barcoNome;

    private String barcoPreco;

    private List<ImageModelDTO> barcoImagens;

    public String getBarcoNome() {
        return barcoNome;
    }

    public void setBarcoNome(String barcoNome) {
        this.barcoNome = barcoNome;
    }

    public String getBarcoPreco() {
        return barcoPreco;
    }

    public void setBarcoPreco(String barcoPreco) {
        this.barcoPreco = barcoPreco;
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
