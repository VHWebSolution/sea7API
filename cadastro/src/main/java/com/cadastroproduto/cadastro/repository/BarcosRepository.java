package com.cadastroproduto.cadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cadastroproduto.cadastro.model.entity.barcos.Barcos;

//TODO verificar se precisa ou nao de repository


public interface BarcosRepository extends JpaRepository<Barcos, String> {
    
}
