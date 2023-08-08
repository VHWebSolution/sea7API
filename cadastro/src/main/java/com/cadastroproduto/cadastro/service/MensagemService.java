package com.cadastroproduto.cadastro.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadastroproduto.cadastro.model.entity.barcos.Barcos;
import com.cadastroproduto.cadastro.model.entity.mensagem.Mensagem;
import com.cadastroproduto.cadastro.model.entity.mensagem.MensagemDTO;
import com.cadastroproduto.cadastro.model.exception.ResourceBadRequestException;
import com.cadastroproduto.cadastro.model.exception.ResourceNotFoundException;
import com.cadastroproduto.cadastro.repository.BarcosRepository;
import com.cadastroproduto.cadastro.repository.MensagemRepository;

@Service
public class MensagemService {

    @Autowired
    MensagemRepository mensagemRepository;

    @Autowired
    BarcosRepository barcosRepository;

    public List<MensagemDTO> buscarTodasMensagens() {
        List<Mensagem> mensagem = mensagemRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        return mensagem.stream()
                .map(mensage -> mapper.map(mensage, MensagemDTO.class))
                .collect(Collectors.toList());
    }

    public MensagemDTO buscarMensagemPorId(String id) {

        Optional<Mensagem> mensagem = mensagemRepository.findById(id);
        if (mensagem.isEmpty() || mensagem == null) {
            throw new ResourceNotFoundException("Não foi possivel encontrar a mensagem de id: " + id);
        }

        MensagemDTO mensagemDTO = new ModelMapper().map(mensagem.get(), MensagemDTO.class);

        return mensagemDTO;
    }

    public MensagemDTO enviarMensagem(MensagemDTO mensagemDTO, String id) {
        if(mensagemDTO.getNomePessoa() == null || mensagemDTO.getNomePessoa().isEmpty()
            || mensagemDTO.getMensagemPessoa() == null || mensagemDTO.getMensagemPessoa().isEmpty()
            || mensagemDTO.getTelefonePessoa() == null || mensagemDTO.getTelefonePessoa().isEmpty()){
                throw new ResourceBadRequestException("Todos os campos devem estar preenchidos");
            }
            
        Mensagem mensagem = new ModelMapper().map(mensagemDTO, Mensagem.class);
        Optional<Barcos> barcos = barcosRepository.findById(id);
        mensagem.setBarcos(barcos.get());
        mensagemRepository.save(mensagem);
        mensagemDTO.setIdMsg(mensagem.getIdMsg());
        mensagemDTO.setBarcos(barcos.get()); // Atribua o objeto Barcos diretamente na MensagemDTO
        return mensagemDTO;
    }

    public void deletarMensagem(String id){
        Optional<Mensagem> mensagem = mensagemRepository.findById(id);
        if (mensagem.isEmpty() || mensagem == null) {
            throw new ResourceNotFoundException("Não foi possivel deletar a mensagem de id: " + id +". Mensagem não localizada!");
        }
        mensagemRepository.deleteById(id);

    }

    public MensagemDTO atualizarMensagem(MensagemDTO mensagemDTO, String id){
         Optional<Mensagem> mensagem = mensagemRepository.findById(id);
        if (mensagem.isEmpty() || mensagem == null) {
            throw new ResourceNotFoundException("Não foi encontrar a mensagem de id: " + id );
        }

        if( mensagemDTO.getNomePessoa() == null  || mensagemDTO.getNomePessoa().isEmpty() ){
         mensagemDTO.setNomePessoa(mensagem.get().getNomePessoa());
        }
        if(mensagemDTO.getMensagemPessoa() == null || mensagemDTO.getMensagemPessoa().isEmpty()){
            mensagemDTO.setMensagemPessoa(mensagem.get().getMensagemPessoa());
        }
        if(mensagemDTO.getTelefonePessoa() == null || mensagemDTO.getTelefonePessoa().isEmpty()){
            mensagemDTO.setTelefonePessoa(mensagem.get().getTelefonePessoa());
        }
        if(mensagemDTO.getBarcos() == null){
            mensagemDTO.setBarcos(mensagem.get().getBarcos());
        }

        
        mensagemDTO.setIdMsg(id);
        Mensagem mensage = new ModelMapper().map(mensagemDTO, Mensagem.class);
        mensagemRepository.save(mensage);
       
        return mensagemDTO;
    }
}
