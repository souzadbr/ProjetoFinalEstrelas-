package br.com.zup.CouchZupper.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErroDeValidacao {
    private String campo;
    private String mensagem;
}
