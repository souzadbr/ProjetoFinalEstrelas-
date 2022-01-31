package br.com.zup.CouchZupper.preferencia;

import br.com.zup.CouchZupper.enums.TipoDePet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class PreferenciaServiceTest {

    @MockBean
    private PreferenciaRepository preferenciaRepository;

    @Autowired
    private PreferenciaService preferenciaService;

    private Preferencia preferencia;

    @BeforeEach
    public void setup() {
        preferencia = new Preferencia();
        preferencia.setId(1);
        preferencia.setTemPet(true);
        preferencia.setTipoDePet(TipoDePet.ANIMAIS_SILVESTRES);
        preferencia.setFumante(false);
        preferencia.setDisponivelParaReceberUmZupper(true);
        preferencia.setConteAlgoQueNaoPerguntamos("Texto de Teste");

    }

    @Test
    public void testarBuscarPreferenciaPorIdCaminhoFeliz() {
        Mockito.when(preferenciaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(preferencia));
        Preferencia buscarPreferenciaPorId = preferenciaService.buscarPreferenciaPorId(Mockito.anyInt());
        Assertions.assertEquals(preferencia.getId(), buscarPreferenciaPorId.getId());

    }
}
