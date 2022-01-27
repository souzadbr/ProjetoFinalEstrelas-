package br.com.zup.CouchZupper.preferencia;

import br.com.zup.CouchZupper.enums.TipoDePet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PreferenciaRepository extends CrudRepository <Preferencia, Integer> {
    List<Preferencia> findAllByTemPet (Boolean temPet);
    List<Preferencia> findAllByFumante (Boolean fumante);
    List<Preferencia> findAllByDisponivelParaReceberUmZupper (Boolean disponivel);
    List<Preferencia> findAllByTipoDePet (TipoDePet tipoDePet);
}
