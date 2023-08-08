package com.cadastroproduto.cadastro.model.error;

public class ErrorMessage {
    // #region Variaveis
    private String titulo;

    private Integer status;

    private String mensagem;
    // #endregion

    // #region Construtor
    public ErrorMessage(String titulo, Integer status, String mensagem) {
        this.titulo = titulo;
        this.status = status;
        this.mensagem = mensagem;
    }
    // #endregion

    // #region Getters and Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    // #endregion

}
