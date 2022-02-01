package br.com.zup.CouchZupper.usuario.dtos;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UsuarioRespostaDTO {
    private String nome;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate dataNascimento;
    private int idade;
    private String telefone;
    private Estado estado;
    private Genero genero;
}
