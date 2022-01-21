package usuario;

import enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private int idade;
    @Column(nullable = false, unique = true)
    private String telefone;
    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private Genero genero;
    @Column(nullable = false)
    private String senha;

}
