package com.cadastroproduto.cadastro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cadastroproduto.cadastro.model.exception.ResourceBadRequestException;
import com.cadastroproduto.cadastro.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService{

    @Autowired
    UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) {     
        if(repository.findByLogin(username) == null ){
            throw new ResourceBadRequestException("Usuario não encontrado!");
        }
        return repository.findByLogin(username);
    }
    
}
