package com.cadastroproduto.cadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cadastroproduto.cadastro.model.entity.barcos.ImageModel;

public interface ImagensRepository extends JpaRepository<ImageModel, String> {
    
}
