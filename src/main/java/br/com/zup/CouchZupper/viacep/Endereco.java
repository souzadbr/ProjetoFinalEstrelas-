package br.com.zup.CouchZupper.viacep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {
    private String cep;
    private String localidade;
    private String uf;
    private Boolean erro = false;
}
