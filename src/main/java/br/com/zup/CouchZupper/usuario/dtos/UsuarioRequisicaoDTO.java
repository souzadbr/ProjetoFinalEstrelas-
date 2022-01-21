package br.com.zup.CouchZupper.usuario.dtos;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UsuarioRequisicaoDTO {
    private String email;
    private String nome;
    private int idade;
    private String telefone;
    private Estado estado;
    private Genero genero;
    private String senha;
    private Preferencia preferencia;
}
