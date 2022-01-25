package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.components.Conversor;
import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import br.com.zup.CouchZupper.security.JWT.JWTComponent;
import br.com.zup.CouchZupper.security.UsuarioLoginService;
import br.com.zup.CouchZupper.usuario.dtos.ResumoCadastroDTO;
import br.com.zup.CouchZupper.usuario.dtos.UsuarioRequisicaoDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;


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
    private Preferencia preferencia, preferenciaRequisicaoDTO;
    private UsuarioRequisicaoDTO usuarioRequisicaoDTO;

    @BeforeEach
    private void setup() {
        objectMapper = new ObjectMapper();

        usuario = new Usuario();
        usuario.setId("000aaa");
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

        usuarioRequisicaoDTO = new UsuarioRequisicaoDTO();
        usuarioRequisicaoDTO.setNome("Usuario Teste");
        usuarioRequisicaoDTO.setEmail("usuario@usuario.com");
        usuarioRequisicaoDTO.setIdade(23);
        usuarioRequisicaoDTO.setTelefone("79999999999");
        usuarioRequisicaoDTO.setEstado(Estado.SERGIPE);
        usuarioRequisicaoDTO.setGenero(Genero.OUTRO);
        usuarioRequisicaoDTO.setSenha("senhateste");

        preferenciaRequisicaoDTO = new Preferencia();
        preferenciaRequisicaoDTO.setId(1);
        preferenciaRequisicaoDTO.setTemPet(true);
        preferenciaRequisicaoDTO.setTipoDePet(TipoDePet.OUTRO);
        preferenciaRequisicaoDTO.setFumante(false);
        preferenciaRequisicaoDTO.setDisponivelParaReceberUmZupper(true);
        preferenciaRequisicaoDTO.setConteAlgoQueNaoPerguntamos("Teste");

        usuarioRequisicaoDTO.setPreferencia(preferenciaRequisicaoDTO);

    }

    private ResultActions realizarRequisicao(Object object, int statusEsperado, String httpVerbo, String complemento) throws Exception {
        String json = objectMapper.writeValueAsString(object);
        URI uri = UriComponentsBuilder.fromPath("/usuario"+complemento).build().toUri();

        return mockMvc.perform(MockMvcRequestBuilders.request(httpVerbo, uri)
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(statusEsperado));
    }

    @Test
    public void testarCadastroDeUsuario() throws Exception {
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioRequisicaoDTO);

        ResultActions resultActions = realizarRequisicao(usuario, 201, "POST","");

        String jsonResposta = resultActions.andReturn().getResponse().getContentAsString();

    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarBuscarUsuarios () throws Exception {
        Mockito.when(usuarioService.buscarUsuarios(Mockito.any(Estado.class))).thenReturn(Arrays.asList(usuario));

        ResultActions resultActions = realizarRequisicao(null, 200, "GET","");

        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        String jsonResposta = resultActions.andReturn().getResponse().getContentAsString();
        List<ResumoCadastroDTO> resumoCadastroDTOS = objectMapper.readValue(
                jsonResposta, new TypeReference<List<ResumoCadastroDTO>>() {
        });
    }

    @Test
    @WithMockUser ("user@user.com")
    public void  testarBuscarUsuarioPorID () throws Exception {
        Mockito.when(usuarioService.buscarUsuarioPorId(Mockito.anyString())).thenReturn(usuario);

        ResultActions resultActions = realizarRequisicao(null, 200, "GET", "/000aaa");

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
    public void testeRotaParaCadastrarUsuarioValidacaoNomeBlank()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setNome(" ");
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
    public void testeRotaParaCadastrarUsuarioValidacaoEmailEmBranco()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setEmail("  ");
        String json = objectMapper.writeValueAsString(usuario);

        ResultActions respostaDaRequisicao = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testeRotaParaCadastrarUsuarioValidacaoEmailNull()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setEmail(null);
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
    public void testeRotaParaCadastrarUsuarioValidacaoTelefoneBlank()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setTelefone("  ");
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

    @Test
    @WithMockUser ("user@user.com")
    public void testeRotaParaCadastrarUsuarioValidacaoEstadoNull()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setEstado(null);
        String json = objectMapper.writeValueAsString(usuario);

        ResultActions respostaDaRequisicao = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testeRotaParaCadastrarUsuarioValidacaoSenhaSize()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuario.setSenha("E");
        String json = objectMapper.writeValueAsString(usuario);

        ResultActions respostaDaRequisicao = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarRotaParaCadastrarGeneroValid()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuario);
        json = json.replace("FEMININO","TESTE");

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarRotaParaCadastrarGeneroBlank()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuario);
        json = json.replace("FEMININO"," ");

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarRotaParaCadastrarEstadoValid()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuario);
        json = json.replace("ACRE","TESTE");

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarRotaParaCadastrarEstadoEmBracno()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuario);
        json = json.replace("ACRE"," ");

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarRotaParaCadastrarPetValido()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuario);
        json = json.replace("GATO","TESTE");

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarRotaParaCadastrarPetEmBranco()throws Exception{
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuario);
        json = json.replace("GATO"," ");

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

}
