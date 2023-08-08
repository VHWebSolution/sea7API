package com.cadastroproduto.cadastro.view.model;

import java.util.List;

public class BarcosRequest {
    private String barcoNome;

    private double barcoPreco;

    private String barcoDescricaoCurta;

    private String barcoDescricaoCompleta;

    private List<ImageModelRequest> barcoImagens;

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

    public List<ImageModelRequest> getBarcoImagens() {
        return barcoImagens;
    }

    public void setBarcoImagens(List<ImageModelRequest> barcoImagens) {
        this.barcoImagens = barcoImagens;
    }

}
