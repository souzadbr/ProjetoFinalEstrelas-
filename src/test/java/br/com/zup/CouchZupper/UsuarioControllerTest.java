package br.com.zup.CouchZupper;

import br.com.zup.CouchZupper.components.Conversor;
import br.com.zup.CouchZupper.usuario.UsuarioController;
import br.com.zup.CouchZupper.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({UsuarioController.class, Conversor.class})
public class UsuarioControllerTest {
    @MockBean
    private UsuarioService usuarioService;
    @Autowired
    private MockMvc mockMvc;


}
