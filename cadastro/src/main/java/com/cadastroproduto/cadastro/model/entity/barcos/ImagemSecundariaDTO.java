package com.cadastroproduto.cadastro.model.entity.barcos;

public class ImagemSecundariaDTO {
    private String idImgSec;

    private String name;

    private String type;

    private String base64Image;

    public String getId() {
        return idImgSec;
    }

    public void setId(String id) {
        this.idImgSec = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
