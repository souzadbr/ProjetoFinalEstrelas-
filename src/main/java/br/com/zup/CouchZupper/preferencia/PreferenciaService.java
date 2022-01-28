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

    public List<Preferencia> listarPreferencias() {
        Iterable<Preferencia> listaPreferencias = preferenciaRepository.findAll();
        return (List<Preferencia>) listaPreferencias;
    }

    public Preferencia atualizarPreferencias(int id, Preferencia preferencia) {
        Preferencia preferenciaAtualizar = buscarPreferenciaPorId(id);

        preferenciaAtualizar.setTemPet(preferencia.isTemPet());
        preferenciaAtualizar.setTipoDePet(preferencia.getTipoDePet());
        preferenciaAtualizar.setFumante(preferenciaAtualizar.isFumante());
        preferenciaAtualizar.setDisponivelParaReceberUmZupper(preferencia.isDisponivelParaReceberUmZupper());
        preferenciaAtualizar.setConteAlgoQueNaoPerguntamos(preferencia.getConteAlgoQueNaoPerguntamos());

        preferenciaRepository.save(preferenciaAtualizar);
        return preferenciaAtualizar;
    }

    public List<Preferencia> buscarPreferencias(Boolean temPet, TipoDePet tipoDePet, Boolean fumante, Boolean disponivel, Usuario usuario) {
        if (temPet != null) {
            return preferenciaRepository.findAllByTemPet(temPet);
        } else if (tipoDePet != null) {
            return preferenciaRepository.findAllByTipoDePet(tipoDePet);
        } else if (fumante != null) {
            return preferenciaRepository.findAllByFumante(fumante);
        } else if (disponivel != null) {
            return preferenciaRepository.findAllByDisponivelParaReceberUmZupper(disponivel);
        }

        return listarPreferencias();
    }
}
