package com.cadastroproduto.cadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cadastroproduto.cadastro.model.entity.mensagem.Mensagem;


public interface MensagemRepository extends JpaRepository<Mensagem, String> {
    
}
