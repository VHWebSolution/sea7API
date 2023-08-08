package com.cadastroproduto.cadastro.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadastroproduto.cadastro.model.entity.mensagem.MensagemDTO;
import com.cadastroproduto.cadastro.model.exception.ResourceBadRequestException;
import com.cadastroproduto.cadastro.service.MensagemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("mensagem")
public class MensagemController {
    @Autowired
    MensagemService mensagemService;

    @GetMapping("/buscar")
    public ResponseEntity<List<MensagemDTO>> buscarTodasMensagens() {
        List<MensagemDTO> mensagemDTO = mensagemService.buscarTodasMensagens();
        return new ResponseEntity<>(mensagemDTO, HttpStatus.OK);
    }

    @GetMapping("/buscar/porId/{id}")
    public ResponseEntity<MensagemDTO> buscarMensagemPorId(@PathVariable String id) {
        MensagemDTO mensagemDTO = mensagemService.buscarMensagemPorId(id);
        return new ResponseEntity<>(mensagemDTO, HttpStatus.OK);
    }

    @PostMapping("/enviar/{id}")
    public ResponseEntity<MensagemDTO> enviarMensagem(@RequestBody @Valid MensagemDTO mensagemDTO,
            @PathVariable String id) {
                String regex = "^[a-zA-Z0-9!.,?@:;()\\s_$-]+$";
        if (!mensagemDTO.getNomePessoa().matches(regex)
                || !mensagemDTO.getMensagemPessoa().matches(regex)) {
            throw new ResourceBadRequestException("Caracter inválido utilizado!");
        }
        mensagemDTO = mensagemService.enviarMensagem(mensagemDTO, id);

        return new ResponseEntity<>(mensagemDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMensagem(@PathVariable String id) {
        mensagemService.deletarMensagem(id);
        return new ResponseEntity<>("Mensagem deletada com sucesso!", HttpStatus.NO_CONTENT);
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<MensagemDTO> atualizarMensagem(@RequestBody MensagemDTO mensagemDTO,
            @PathVariable String id) {
        String regex = "^[a-zA-Z0-9!.,?@:;()\\s_$-]+$";
        if ((mensagemDTO.getNomePessoa() != null && !mensagemDTO.getNomePessoa().isEmpty()
                && !mensagemDTO.getNomePessoa().matches(regex))
                || (mensagemDTO.getMensagemPessoa() != null && !mensagemDTO.getMensagemPessoa().isEmpty()
                && !mensagemDTO.getMensagemPessoa().matches(regex))) {
            throw new ResourceBadRequestException("Caracter inválido utilizado!");
        }
        mensagemDTO = mensagemService.atualizarMensagem(mensagemDTO, id);
        return new ResponseEntity<>(mensagemDTO, HttpStatus.CREATED);
    }
}
