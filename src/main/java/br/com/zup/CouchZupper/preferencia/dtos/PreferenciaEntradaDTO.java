package br.com.zup.CouchZupper.preferencia.dtos;

import br.com.zup.CouchZupper.enums.TipoDePet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PreferenciaEntradaDTO {
    private boolean temPet;
    private TipoDePet tipoDePet;
    private boolean fumante;
    private boolean disponivelParaReceberUmZupper;
    private String conteAlgoQueNaoPerguntamos;
}
