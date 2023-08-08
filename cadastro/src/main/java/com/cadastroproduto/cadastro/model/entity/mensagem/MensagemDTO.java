package com.cadastroproduto.cadastro.model.entity.mensagem;




import com.cadastroproduto.cadastro.model.entity.barcos.Barcos;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensagemDTO {

        private String idMsg;
        
        private String nomePessoa;
        @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{4}-\\d{4}", message = "Telefone inválido. O formato deve ser (XX) XXXX-XXXX.")
        private String telefonePessoa;

        private String mensagemPessoa;

        private Barcos barcos;
}
