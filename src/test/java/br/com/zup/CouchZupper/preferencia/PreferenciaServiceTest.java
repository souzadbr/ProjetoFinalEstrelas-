package br.com.zup.CouchZupper.preferencia;

import br.com.zup.CouchZupper.enums.TipoDePet;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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



}
