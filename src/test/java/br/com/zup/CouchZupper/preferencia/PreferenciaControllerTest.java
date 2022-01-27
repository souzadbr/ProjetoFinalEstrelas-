package br.com.zup.CouchZupper;

import br.com.zup.CouchZupper.components.Conversor;
import br.com.zup.CouchZupper.enums.TipoDePet;;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import br.com.zup.CouchZupper.preferencia.PreferenciaController;
import br.com.zup.CouchZupper.preferencia.PreferenciaService;
import br.com.zup.CouchZupper.preferencia.dtos.PreferenciaEntradaDTO;
import br.com.zup.CouchZupper.security.JWT.JWTComponent;
import br.com.zup.CouchZupper.security.UsuarioLoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@WebMvcTest({PreferenciaController.class, Conversor.class, JWTComponent.class, UsuarioLoginService.class})
public class PreferenciaControllerTest {
    @MockBean
    private PreferenciaService preferenciaService;
    @MockBean
    private JWTComponent jwtComponent;
    @MockBean
    private UsuarioLoginService usuarioLoginService;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Preferencia preferencia;
    private PreferenciaEntradaDTO preferenciaEntradaDTO;

    @BeforeEach
    private void setup() {
        objectMapper = new ObjectMapper();

        preferencia = new Preferencia();
        preferencia.setFumante(true);
        preferencia.setDisponivelParaReceberUmZupper(true);
        preferencia.setTemPet(true);
        preferencia.setTipoDePet(TipoDePet.GATO);
    }

    private ResultActions realizarRequisicao(Object object, int statusEsperado, String httpVerbo, String complemento) throws Exception {
        String json = objectMapper.writeValueAsString(object);
        URI uri = UriComponentsBuilder.fromPath("/preferencias" + complemento).build().toUri();

        return mockMvc.perform(MockMvcRequestBuilders.request(httpVerbo, uri)
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(statusEsperado));
    }

}