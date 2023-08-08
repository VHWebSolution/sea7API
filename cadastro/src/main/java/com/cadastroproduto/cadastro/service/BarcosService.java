package com.cadastroproduto.cadastro.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cadastroproduto.cadastro.model.entity.barcos.Barcos;
import com.cadastroproduto.cadastro.model.entity.barcos.BarcosDTO;
import com.cadastroproduto.cadastro.model.entity.barcos.ImageModel;
import com.cadastroproduto.cadastro.model.entity.barcos.ImageModelDTO;
import com.cadastroproduto.cadastro.model.entity.barcos.ImagemSecundaria;
import com.cadastroproduto.cadastro.model.entity.barcos.ImagemSecundariaDTO;
import com.cadastroproduto.cadastro.model.exception.ResourceNotFoundException;
import com.cadastroproduto.cadastro.repository.BarcosRepository;
import com.cadastroproduto.cadastro.repository.ImagemSecundariaRepository;
import com.cadastroproduto.cadastro.repository.ImagensRepository;

@Service
public class BarcosService {

    @Autowired
    BarcosRepository barcosRepository;

    @Autowired
    ImagensRepository imagensRepository;

    @Autowired
    ImagemSecundariaRepository imagemSecundariaRepository;

    // #region Obter Todos
    public List<BarcosDTO> obterTodos() {
        List<Barcos> barcos = barcosRepository.findAll();

        List<BarcosDTO> barcosDTO = new ArrayList<>();

        for (Barcos barco : barcos) {
            BarcosDTO dto = new BarcosDTO();
            dto.setId(barco.getId());
            dto.setBarcoNome(barco.getBarcoNome());
            dto.setBarcoPreco(barco.getBarcoPreco());
            dto.setBarcoDescricaoCurta(barco.getBarcoDescricaoCurta());
            dto.setBarcoDescricaoCompleta(barco.getBarcoDescricaoCompleta());

            List<ImageModelDTO> imagensDTO = new ArrayList<>();
            for (ImageModel imagem : barco.getBarcoImagem()) {
                ImageModelDTO imagemDTO = new ImageModelDTO();
                imagemDTO.setId(imagem.getIdImg());
                imagemDTO.setName(imagem.getName());
                imagemDTO.setType(imagem.getType());
                imagemDTO.setBase64Image(Base64.getEncoder().encodeToString(imagem.getPicByte()));
                imagensDTO.add(imagemDTO);
            }

            List<ImagemSecundariaDTO> imagemSecundariaDTO = new ArrayList<>();
            for(ImagemSecundaria imagemSec : barco.getImagemSecundaria()) {
                ImagemSecundariaDTO imagemSecDTO = new ImagemSecundariaDTO();
                imagemSecDTO.setId(imagemSec.getIdImgSec());
                imagemSecDTO.setName(imagemSec.getName());
                imagemSecDTO.setType(imagemSec.getType());
                imagemSecDTO.setBase64Image(Base64.getEncoder().encodeToString(imagemSec.getPicByte()));
                imagemSecundariaDTO.add(imagemSecDTO);
            }

            dto.setBarcoImagens(imagensDTO);
            dto.setImagemSecundaria(imagemSecundariaDTO);
            barcosDTO.add(dto);
        }

        return barcosDTO;
    }
    // #endregion

    // #region ObterPorID
    public Optional<BarcosDTO> obterPorId(String id) {
        Optional<Barcos> barco = barcosRepository.findById(id);

        if (barco.isEmpty()) {
            throw new ResourceNotFoundException("Produto com id: " + id + " não encontrado");
        }
        BarcosDTO barcosDTO = new ModelMapper().map(barco.get(), BarcosDTO.class);

        // Recupere a imagem associada ao barco, se existir
        if (barco.get().getBarcoImagem() != null && !barco.get().getBarcoImagem().isEmpty()) {
            ImageModel imagem = barco.get().getBarcoImagem().iterator().next();
            ImageModelDTO imagemDTO = new ImageModelDTO();
            imagemDTO.setId(imagem.getIdImg());
            imagemDTO.setName(imagem.getName());
            imagemDTO.setType(imagem.getType());
            imagemDTO.setBase64Image(Base64.getEncoder().encodeToString(imagem.getPicByte()));
            barcosDTO.setBarcoImagens(Collections.singletonList(imagemDTO));
        }

        if (barco.get().getImagemSecundaria() != null && !barco.get().getImagemSecundaria().isEmpty()) {
            ImagemSecundaria imagemSec = barco.get().getImagemSecundaria().iterator().next();
            ImagemSecundariaDTO imagemSecDTO = new ImagemSecundariaDTO();
            imagemSecDTO.setId(imagemSec.getIdImgSec());
            imagemSecDTO.setName(imagemSec.getName());
            imagemSecDTO.setType(imagemSec.getType());
            imagemSecDTO.setBase64Image(Base64.getEncoder().encodeToString(imagemSec.getPicByte()));
            barcosDTO.setImagemSecundaria(Collections.singletonList(imagemSecDTO));
        }

        return Optional.of(barcosDTO);
    }
    // #endregion

    public BarcosDTO adicionar(BarcosDTO barcosDTO, MultipartFile[] files, MultipartFile[] filesSec) {
        try {
            Set<ImageModel> images = uploadImage(files);
            Set<ImagemSecundaria> imagesSec = null;
    
            if (filesSec != null && filesSec.length > 0) {
                imagesSec = uploadImageSec(filesSec);
    
                List<ImagemSecundariaDTO> imagemSecundariaDTO = new ArrayList<>();
                for (ImagemSecundaria imagens : imagesSec) {
                    ImagemSecundariaDTO imagemSecundariaDTOs = new ImagemSecundariaDTO();
                    imagemSecundariaDTOs.setId(imagens.getIdImgSec());
                    imagemSecundariaDTOs.setName(imagens.getName());
                    imagemSecundariaDTOs.setType(imagens.getType());
                    imagemSecundariaDTOs.setBase64Image(Base64.getEncoder().encodeToString(imagens.getPicByte()));
                    imagemSecundariaDTO.add(imagemSecundariaDTOs);
                }
                barcosDTO.setImagemSecundaria(imagemSecundariaDTO);
            } else {
                barcosDTO.setImagemSecundaria(null);
            }
    
            List<ImageModelDTO> imagensDTO = new ArrayList<>();
            for (ImageModel imagem : images) {
                ImageModelDTO imagemDTO = new ImageModelDTO();
                imagemDTO.setId(imagem.getIdImg());
                imagemDTO.setName(imagem.getName());
                imagemDTO.setType(imagem.getType());
                imagemDTO.setBase64Image(Base64.getEncoder().encodeToString(imagem.getPicByte()));
                imagensDTO.add(imagemDTO);
            }
            barcosDTO.setBarcoImagens(imagensDTO);

            // Convertendo o DTO para a entidade Barcos e chamando o serviço para adicionar
            Barcos barcos = new ModelMapper().map(barcosDTO, Barcos.class);
            barcos.setBarcoImagem(images);
            barcos.setImagemSecundaria(imagesSec);
            barcosRepository.save(barcos);
            barcosDTO.setId(barcos.getId());

            return barcosDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();

        for (MultipartFile file : multipartFiles) {
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes());
            imageModels.add(imageModel);
        }

        return imageModels;
    }

    public Set<ImagemSecundaria> uploadImageSec(MultipartFile[] multipartFiles) throws IOException {
        Set<ImagemSecundaria> imageModels = new HashSet<>();

        for (MultipartFile file : multipartFiles) {
            ImagemSecundaria imageModel = new ImagemSecundaria(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes());
            imageModels.add(imageModel);
        }

        return imageModels;
    }

    public void deletar(String id) {
        Optional<Barcos> barco = barcosRepository.findById(id);
        if (barco.isEmpty()) {
            throw new ResourceNotFoundException(
                    "ERRO: Não foi possivel deletar o produto de id: " + id + ". Produto não existe!");
        }
        barcosRepository.deleteById(id);
    }

    public BarcosDTO atualizar(String id, BarcosDTO barcosDTO, MultipartFile[] files, MultipartFile[] filesSec) {
        
        Optional<Barcos> barcosOptional = barcosRepository.findById(id);
        if (barcosOptional.isEmpty()) {
            throw new ResourceNotFoundException(
                    "ERRO: Não foi possivel deletar o produto de id: " + id + ". Produto não existe!");
        }
        try {

            Barcos barco = barcosOptional.get();

            // Atualizar os atributos do barco com os valores do barcosDTO
            if (barcosDTO.getBarcoNome().isEmpty() || barcosDTO.getBarcoNome() == null) {
                barco.setBarcoNome(barco.getBarcoNome());
            } else {
                barco.setBarcoNome(barcosDTO.getBarcoNome());
            }
            if (barcosDTO.getBarcoPreco() == 0.0 || barcosDTO.getBarcoPreco() == 0) {
                barco.setBarcoPreco(barco.getBarcoPreco());
            } else {
                barco.setBarcoPreco(barcosDTO.getBarcoPreco());
            }
            if (barcosDTO.getBarcoDescricaoCurta() == null || barcosDTO.getBarcoDescricaoCurta().isEmpty() ) {
                barco.setBarcoDescricaoCurta(barco.getBarcoDescricaoCurta());
            } else {
                barco.setBarcoDescricaoCurta(barcosDTO.getBarcoDescricaoCurta());
            }
            if (barcosDTO.getBarcoDescricaoCompleta() == null || barcosDTO.getBarcoDescricaoCompleta().isEmpty()) {
                barco.setBarcoDescricaoCompleta(barco.getBarcoDescricaoCompleta());
            } else {
                barco.setBarcoDescricaoCompleta(barcosDTO.getBarcoDescricaoCompleta());
            }

            Set<ImageModel> images = null;
            // Atualizar as imagens (se necessário)
            if (files != null && files.length > 0) {
                images = uploadImage(files);
                
            } else {
                images = barco.getBarcoImagem();
            }
        
             Set<ImagemSecundaria> imagesSec = null;
            if (filesSec != null && filesSec.length > 0) {
                imagesSec = uploadImageSec(filesSec);
                imagesSec.addAll(barco.getImagemSecundaria());
            } else{
                imagesSec = barco.getImagemSecundaria();
            }
           

            barco.setBarcoImagem(images);
            barco.setImagemSecundaria(imagesSec);
            // Salvar a entidade atualizada
            barcosRepository.save(barco);
            
            // Atualizar o DTO com os dados atualizados (opcional, se desejar retornar)
            // Você pode também criar um novo DTO caso prefira
            
           
            barcosDTO = new ModelMapper().map(barco, BarcosDTO.class);
            barcosDTO.setBarcoImagens(getImageModelDTOs(barco.getBarcoImagem()));
            barcosDTO.setImagemSecundaria(getImagemSecundariaDTO(barco.getImagemSecundaria()));
           
            return barcosDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Método auxiliar para converter um conjunto de ImageModel para uma lista de
    // ImageModelDTO
    private List<ImageModelDTO> getImageModelDTOs(Set<ImageModel> images) {
        List<ImageModelDTO> imagensDTO = new ArrayList<>();
        for (ImageModel imagem : images) {
            ImageModelDTO imagemDTO = new ImageModelDTO();
            imagemDTO.setId(imagem.getIdImg());
            imagemDTO.setName(imagem.getName());
            imagemDTO.setType(imagem.getType());
            imagemDTO.setBase64Image(Base64.getEncoder().encodeToString(imagem.getPicByte()));
            imagensDTO.add(imagemDTO);
        }
        return imagensDTO;
    }

     private List<ImagemSecundariaDTO> getImagemSecundariaDTO(Set<ImagemSecundaria> images) {
        List<ImagemSecundariaDTO> imagensDTO = new ArrayList<>();
        for (ImagemSecundaria imagem : images) {
            ImagemSecundariaDTO imagemDTO = new ImagemSecundariaDTO();
            imagemDTO.setId(imagem.getIdImgSec());
            imagemDTO.setName(imagem.getName());
            imagemDTO.setType(imagem.getType());
            imagemDTO.setBase64Image(Base64.getEncoder().encodeToString(imagem.getPicByte()));
            imagensDTO.add(imagemDTO);
        }
        return imagensDTO;
    }

    public void deletarImagemPrincipal(String id){
        Optional<ImageModel> imagOptional = imagensRepository.findById(id);
        if(imagOptional.isEmpty()){
            throw new ResourceNotFoundException("Não foi possivel encontrar a imagem de id: "+id);
        }

        ImageModel imageModel = imagOptional.get();
    
        for (Barcos barco : barcosRepository.findAll()) {
            barco.getBarcoImagem().remove(imageModel);
        }

        imagensRepository.deleteById(id);   
    }

    public void deletarImagemSecundaria(String id) {
        Optional<ImagemSecundaria> imageOptional = imagemSecundariaRepository.findById(id);
        if (imageOptional.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possivel encontrar a imagem de id: " + id);
        }
    
        ImagemSecundaria imagemSecundaria = imageOptional.get();
    
        for (Barcos barco : barcosRepository.findAll()) {
            barco.getImagemSecundaria().remove(imagemSecundaria);
        }
    
        imagemSecundariaRepository.deleteById(id);
    }
}
