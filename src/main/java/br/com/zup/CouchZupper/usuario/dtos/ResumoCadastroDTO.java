package br.com.zup.CouchZupper.usuario.dtos;

import br.com.zup.CouchZupper.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumoCadastroDTO {
    private String id;
    @Size(min=2, max=50, message = "{validacao.nome.size}")
    @NotNull(message = "{validacao.nome.not-null}")
    @NotBlank(message = "{validacao.nome.not-blank}")
    private String nome;
    @DecimalMin(value = "18",message = "{validacao.idade.decimal-min}")
    private int idade;
    private String uf;
    private String localidade;
}
