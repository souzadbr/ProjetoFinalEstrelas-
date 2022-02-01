package br.com.zup.CouchZupper.usuario.dtos;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequisicaoDTO {
    private String email;
    @Size(min = 2, max = 50, message = "{validacao.nome.size}")
    @NotNull(message = "{validacao.nome.not-null}")
    @NotBlank(message = "{validacao.nome.not-blank}")
    private String nome;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate dataNascimento;
    @NotNull(message = "{validacao.telefone.not-null}")
    @NotBlank(message = "{validacao.telefone.not-blank}")
    @Pattern(regexp = "^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$", message = "{validacao.telefone.padrao}")
    private String telefone;
    @Size( min = 8,max = 8, message = "{validacao.cep.size}")
    private String cep;
    @Valid
    @NotNull(message = "{validacao.genero.not-null}")
    private Genero genero;
    @Size(min = 6, message = "{validacao.senha.size}")
    private String senha;
    private Preferencia preferencia;

}
