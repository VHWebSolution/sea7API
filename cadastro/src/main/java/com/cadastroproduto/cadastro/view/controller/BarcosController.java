package com.cadastroproduto.cadastro.view.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;


import com.cadastroproduto.cadastro.model.entity.barcos.BarcosDTO;
import com.cadastroproduto.cadastro.model.exception.ResourceBadRequestException;
import com.cadastroproduto.cadastro.service.BarcosService;
import com.cadastroproduto.cadastro.view.model.BarcosResponse;
import com.cadastroproduto.cadastro.view.model.InfoBasicaResponse;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("barcos")
public class BarcosController {

    @Autowired
    BarcosService barcosService;

    @GetMapping("")
    public String whiteLabel() {
        return "<h1> API de controle de barcos </h1> <br>"
                + "Buscar a informação completa de todos os barcos: /buscar <br>"
                + "Buscar a informação completa de um barco pelo Id: /buscar/porId/{id} <br>"
                + "Buscar a informação básica de todos os barcos: /buscar/infoBasica <br>"
                + "Buscar a informação básica de um barco pelo Id: /buscar/infoBasica/porId/{id} <br>"
                + "Cadastrar informações de um barco: /salvar <br>"
                + "Deletar um barco: /deletar/{id} <br>"
                + "Atualizar as informações de um barco: /atualizar/{id}";
    }

  

    @GetMapping("/buscar")
    public ResponseEntity<List<BarcosResponse>> obterTodos() {
        List<BarcosDTO> barcosDTO = barcosService.obterTodos();
        ModelMapper mapper = new ModelMapper();
        List<BarcosResponse> resposta = barcosDTO.stream()
                .map(barco -> mapper.map(barco, BarcosResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/buscar/infoBasica")
    public ResponseEntity<List<InfoBasicaResponse>> obterTodasInfoBasicas() {
        List<BarcosDTO> barcosDTO = barcosService.obterTodos();
        ModelMapper mapper = new ModelMapper();
        List<InfoBasicaResponse> resposta = barcosDTO.stream()
                .map(barcos -> mapper.map(barcos, InfoBasicaResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/buscar/porId/{id}")
    public ResponseEntity<Optional<BarcosResponse>> obterPorId(@PathVariable String id) {
        Optional<BarcosDTO> barcosDTO = barcosService.obterPorId(id);

        BarcosResponse resposta = new ModelMapper().map(barcosDTO.get(), BarcosResponse.class);
        return new ResponseEntity<>(Optional.of(resposta), HttpStatus.OK);
    }

    @GetMapping("/buscar/infoBasica/porId/{id}")
    public ResponseEntity<Optional<InfoBasicaResponse>> obterInfoBasicaPorId(@PathVariable String id) {
        Optional<BarcosDTO> barcosDTO = barcosService.obterPorId(id);

        InfoBasicaResponse resposta = new ModelMapper().map(barcosDTO.get(), InfoBasicaResponse.class);
        return new ResponseEntity<>(Optional.of(resposta), HttpStatus.OK);
    }

    @PostMapping(value = { "/salvar" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<BarcosResponse> adicionar(@ModelAttribute @Valid BarcosDTO barcosDTO,
            @RequestParam("barcoImagem") @Valid MultipartFile[] files,
            @RequestParam(value = "barcoImagensSec", required = false) @Valid MultipartFile[] filesSec) {
        String regex = "^[a-zA-Z0-9!.,?@:;()\\s_$-]+$";

        if (Arrays.stream(files).anyMatch(file -> file.isEmpty())
                || barcosDTO.getBarcoDescricaoCompleta().trim().isEmpty()
                || barcosDTO.getBarcoDescricaoCurta().trim().isEmpty()
                || barcosDTO.getBarcoNome().trim().isEmpty()
                || barcosDTO.getBarcoPreco() == 0) {
            throw new ResourceBadRequestException("Preencha todos os campos vazios!");
        }
        if (!barcosDTO.getBarcoNome().matches(regex)
                || !barcosDTO.getBarcoDescricaoCompleta().matches(regex)
                || !barcosDTO.getBarcoDescricaoCurta().matches(regex)) {
            throw new ResourceBadRequestException("Caracter inválido utilizado!");
        }

        barcosDTO = barcosService.adicionar(barcosDTO, files, filesSec);
        return new ResponseEntity<>(new ModelMapper().map(barcosDTO, BarcosResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        barcosService.deletar(id);
        return new ResponseEntity<>("Barco deletado com sucesso!", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deletar/imagemPrincipal/{id}")
    public ResponseEntity<?> deletarImagemPrincipal(@PathVariable String id){
        barcosService.deletarImagemPrincipal(id);
        return new ResponseEntity<>("Imagem principal deletada com sucesso!", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deletar/imagemSecundaria/{id}")
    public ResponseEntity<?> deletarImagemSecundaria(@PathVariable String id){
        barcosService.deletarImagemSecundaria(id);
        return new ResponseEntity<>("Imagem principal deletada com sucesso!", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "atualizar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BarcosResponse> atualizar(@PathVariable String id,
            @ModelAttribute BarcosDTO barcosDTO,
            @RequestParam(value = "barcoImagem", required=false) MultipartFile[] files,
            @RequestParam(value = "barcoImagensSec", required = false) @Valid MultipartFile[] filesSec) {
        String regex = "^[a-zA-Z0-9!.,?@:;()\\s_$-]+$";
        if ((barcosDTO.getBarcoNome() != null && !barcosDTO.getBarcoNome().isEmpty()
                && !barcosDTO.getBarcoNome().matches(regex))
                || (barcosDTO.getBarcoDescricaoCompleta() != null && !barcosDTO.getBarcoDescricaoCompleta().isEmpty()
                        && !barcosDTO.getBarcoDescricaoCompleta().matches(regex))
                || (barcosDTO.getBarcoDescricaoCurta() != null && !barcosDTO.getBarcoDescricaoCurta().isEmpty()
                        && !barcosDTO.getBarcoDescricaoCurta().matches(regex))) {
            throw new ResourceBadRequestException("Caracter inválido utilizado!");
        }

        barcosDTO = barcosService.atualizar(id, barcosDTO, files, filesSec);
        return new ResponseEntity<>(new ModelMapper().map(barcosDTO, BarcosResponse.class), HttpStatus.CREATED);
    }
}
