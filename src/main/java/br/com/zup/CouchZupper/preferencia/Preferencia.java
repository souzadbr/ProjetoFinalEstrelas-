package br.com.zup.CouchZupper.preferencia;


import br.com.zup.CouchZupper.enums.TipoDePet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "preferencias")
public class Preferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private boolean temPet;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDePet tipoDePet;
    @Column(nullable = false)
    private boolean fumante;
    @Column(nullable = false)
    private boolean disponivelParaReceberUmZupper;
    @Column(nullable = true)
    private String conteAlgoQueNaoPerguntamos;

}
