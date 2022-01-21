package preferencia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "preferencias")
public class Preferencias {

    private int id;
    private boolean TemPet;
    private TipoDePet tipoDePet;
    private boolean fumante;
    private boolean disponivelParaReceberUmZupper;

}
