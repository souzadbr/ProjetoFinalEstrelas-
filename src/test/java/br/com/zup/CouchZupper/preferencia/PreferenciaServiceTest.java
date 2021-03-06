package br.com.zup.CouchZupper.preferencia;

import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.exception.PreferenciaNaoLocalizadaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;



@SpringBootTest
public class PreferenciaServiceTest {

    @MockBean
    private PreferenciaRepository preferenciaRepository;

    @Autowired
    private PreferenciaService preferenciaService;

    private Preferencia preferencia, preferenciaAtualizada;

    @BeforeEach
    public void setup() {
        preferencia = new Preferencia();
        preferencia.setId(1);
        preferencia.setTemPet(true);
        preferencia.setTipoDePet(TipoDePet.ANIMAIS_SILVESTRES);
        preferencia.setFumante(true);
        preferencia.setDisponivelParaReceberUmZupper(true);
        preferencia.setConteAlgoQueNaoPerguntamos("Texto de Teste");

        preferenciaAtualizada = new Preferencia();

    }

    @Test
    public void testarBuscarPreferenciaPorIdCaminhoFeliz() {
        Mockito.when(preferenciaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(preferencia));
        Preferencia buscarPreferenciaPorId = preferenciaService.buscarPreferenciaPorId(Mockito.anyInt());
        Assertions.assertEquals(preferencia.getId(), buscarPreferenciaPorId.getId());

    }

    @Test
    public void testarBuscarPreferenciaPorIdCaminhoTriste() {
        Mockito.when(preferenciaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        PreferenciaNaoLocalizadaException preferenciaNaoLocalizadaException =
                Assertions.assertThrows(PreferenciaNaoLocalizadaException.class, () ->
                        {
                            preferenciaService.buscarPreferenciaPorId(77879988);
                        }
                );
    }

    @Test
    public void testarAtualizarPreferenciasCaminhoTriste() {
        Mockito.when(preferenciaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        PreferenciaNaoLocalizadaException exception =
                Assertions.assertThrows(PreferenciaNaoLocalizadaException.class, () -> {
                    preferenciaService.atualizarPreferencias(1236985, Mockito.any(Preferencia.class));
                });
        Mockito.verify(preferenciaRepository, Mockito.times(0))
                .save(Mockito.any(Preferencia.class));

    }

    @Test
    public void testarAtualizarPreferenciasCaminhoFeliz() {
        Mockito.when(preferenciaRepository.save(Mockito.any())).thenReturn(preferencia);
        Mockito.when(preferenciaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(preferencia));

        preferenciaService.atualizarPreferencias(Mockito.anyInt(), preferencia);

        Mockito.verify(preferenciaRepository, Mockito.times(1)).save(Mockito.any());
    }

}