package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import br.com.zup.CouchZupper.preferencia.Preferencia;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private LocalDate dataNascimento;
    @Column(nullable = false, unique = true)
    private String telefone;
   @Column(nullable = false)
    private String uf;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    private String localidade;
    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private Genero genero;
    @Column(nullable = false)
    private String senha;
    @OneToOne (cascade =  CascadeType.ALL)
    private Preferencia preferencia;

}
