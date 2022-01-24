package br.com.zup.CouchZupper;

import br.com.zup.CouchZupper.components.Conversor;
import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import br.com.zup.CouchZupper.security.JWT.JWTComponent;
import br.com.zup.CouchZupper.security.UsuarioLoginService;
import br.com.zup.CouchZupper.usuario.Usuario;
import br.com.zup.CouchZupper.usuario.UsuarioController;
import br.com.zup.CouchZupper.usuario.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest({UsuarioController.class, Conversor.class, JWTComponent.class, UsuarioLoginService.class})
public class UsuarioControllerTest {
    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private JWTComponent jwtComponent;
    @MockBean
    private UsuarioLoginService usuarioLoginService;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Usuario usuario;
    private Preferencia preferencia;

    @BeforeEach
    private void setup() {
        objectMapper = new ObjectMapper();

        usuario = new Usuario();
        usuario.setNome("Debora Rodrigues");
        usuario.setEmail("debora@gmail.com");
        usuario.setEstado(Estado.ACRE);
        usuario.setGenero(Genero.FEMININO);
        usuario.setIdade(18);
        usuario.setTelefone("920026789");
        usuario.setSenha("minhasenha");

        preferencia = new Preferencia();
        preferencia.setFumante(true);
        preferencia.setDisponivelParaReceberUmZupper(true);
        preferencia.setTemPet(true);
        preferencia.setTipoDePet(TipoDePet.GATO);

        usuario.setPreferencia(preferencia);

    }

    @Test
    @WithMockUser ("user@user.com")
    public void testeRotaParaCadastrarUsuarioValidacaoNomeNull()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setNome(null);
        String json = objectMapper.writeValueAsString(usuario);

        ResultActions respostaDaRequisicao = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testeRotaParaCadastrarUsuarioValidacaoNomeSize()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setNome("E");
        String json = objectMapper.writeValueAsString(usuario);

        ResultActions respostaDaRequisicao = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testeRotaParaCadastrarUsuarioValidacaoIdadeMinima()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setIdade(12);
        String json = objectMapper.writeValueAsString(usuario);

        ResultActions respostaDaRequisicao = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testeRotaParaCadastrarUsuarioValidacaoEmailValido()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setEmail("deborasouza");
        String json = objectMapper.writeValueAsString(usuario);

        ResultActions respostaDaRequisicao = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testeRotaParaCadastrarUsuarioValidacaoTelefoneNull()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setTelefone(null);
        String json = objectMapper.writeValueAsString(usuario);

        ResultActions respostaDaRequisicao = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testeRotaParaCadastrarUsuarioValidacaoGeneroNull()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setGenero(null);
        String json = objectMapper.writeValueAsString(usuario);

        ResultActions respostaDaRequisicao = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }
}
