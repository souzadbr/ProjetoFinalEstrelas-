package br.com.zup.CouchZupper.usuario.dtos;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UsuarioRequisicaoDTO {
    @Email(message = "{validacao.email.email}")
    @NotNull(message = "{validacao.email.not-null}")
    private String email;
    @Size(min=2, max=50, message = "{validacao.nome.size}")
    @NotNull(message = "{validacao.nome.not-null}")
    @NotBlank(message = "{validacao.nome.not-blank}")
    private String nome;
    @DecimalMin(value = "18",message = "{validacao.idade.decimal-min}")
    private int idade;
    @NotNull(message = "{validacao.telefone.not-null}")
    @NotBlank(message = "{validacao.telefone.not-blank}")
    private String telefone;
    @Valid
    @NotNull(message = "{validacao.estado.not-null}")
    private Estado estado;
    @Valid
    @NotNull(message = "{validacao.genero.not-null}")
    private Genero genero;
    @Size(min = 6, message = "{validacao.senha.size}")
    private String senha;
    private Preferencia preferencia;
}
