package br.com.zup.CouchZupper.preferencia;

import br.com.zup.CouchZupper.exception.PreferenciaNaoLocalizadaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PreferenciaService {
    @Autowired
    private PreferenciaRepository preferenciaRepository;
    public Preferencia buscarPreferenciaPorId(int id) {
        Optional<Preferencia> preferenciaOptional = preferenciaRepository.findById(id);

        if (preferenciaOptional.isEmpty()){
            throw new PreferenciaNaoLocalizadaException();
        }
        return preferenciaOptional.get();
    }
}
