package br.com.zup.CouchZupper.preferencia;

import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.exception.PreferenciaNaoLocalizadaException;
import br.com.zup.CouchZupper.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreferenciaService {
    @Autowired
    private PreferenciaRepository preferenciaRepository;

    public Preferencia buscarPreferenciaPorId(int id) {
        Optional<Preferencia> preferenciaOptional = preferenciaRepository.findById(id);

        if (preferenciaOptional.isEmpty()) {
            throw new PreferenciaNaoLocalizadaException();
        }
        return preferenciaOptional.get();

    }

    public Preferencia atualizarPreferencias(int id, Preferencia preferencia) {
        Preferencia preferenciaAtualizar = buscarPreferenciaPorId(id);

        preferenciaAtualizar.setTemPet(preferencia.isTemPet());
        preferenciaAtualizar.setTipoDePet(preferencia.getTipoDePet());
        preferenciaAtualizar.setFumante(preferencia.isFumante());
        preferenciaAtualizar.setDisponivelParaReceberUmZupper(preferencia.isDisponivelParaReceberUmZupper());
        preferenciaAtualizar.setConteAlgoQueNaoPerguntamos(preferencia.getConteAlgoQueNaoPerguntamos());

        preferenciaRepository.save(preferenciaAtualizar);
        return preferenciaAtualizar;
    }

}
