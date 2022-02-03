package br.com.zup.CouchZupper.usuario;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComparacaoUsuarioDTO {
    private String id;
    private String nome;
    private String uf;
    private double porcentagemMatch;

}
