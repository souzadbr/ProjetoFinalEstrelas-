package br.com.zup.CouchZupper.usuario.dtos;

import br.com.zup.CouchZupper.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumoCadastroDTO {
    private String id;
    private String nome;
    private int idade;
    private Estado estado;
}
