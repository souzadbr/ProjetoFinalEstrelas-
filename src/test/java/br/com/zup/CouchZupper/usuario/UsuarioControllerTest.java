package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.components.Conversor;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.exception.UsuarioNaoLocalizadoException;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import br.com.zup.CouchZupper.security.JWT.JWTComponent;
import br.com.zup.CouchZupper.security.UsuarioLoginService;
import br.com.zup.CouchZupper.usuario.dtos.ResumoCadastroDTO;
import br.com.zup.CouchZupper.usuario.dtos.UsuarioAtualizarDadosDTO;
import br.com.zup.CouchZupper.usuario.dtos.UsuarioRequisicaoDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.time.LocalDate;
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
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        usuario = new Usuario();
        usuario.setId("000aaa");
        usuario.setNome("Debora Rodrigues");
        usuario.setEmail("debora@gmail.com");
        usuario.setUf("SE");
        usuario.setGenero(Genero.FEMININO);
        usuario.setDataNascimento(LocalDate.of(2010, 3, 7));
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
        usuarioRequisicaoDTO.setDataNascimento(LocalDate.of(2010, 3, 7));
        usuarioRequisicaoDTO.setTelefone("79999999999");
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

        ResultActions resultadoEsperado = realizarRequisicao(usuario, 201, "POST","");

        String jsonResposta = resultadoEsperado.andReturn().getResponse().getContentAsString();
        Mockito.verify(usuarioService, Mockito.times(1)).salvarUsuario(Mockito.any(Usuario.class));
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarBuscarUsuarios () throws Exception {
        Mockito.when(usuarioService.buscarUsuarios(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(Genero.class),
                Mockito.anyBoolean(),
                Mockito.anyBoolean(),
                Mockito.any(TipoDePet.class)))
                .thenReturn(Arrays.asList(usuario)
        );

        ResultActions resultadoEsperado = realizarRequisicao(null, 200, "GET","");

        resultadoEsperado.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        String jsonResposta = resultadoEsperado.andReturn().getResponse().getContentAsString();
        List<ResumoCadastroDTO> resumoCadastroDTOS = objectMapper.readValue(
                jsonResposta, new TypeReference<List<ResumoCadastroDTO>>() {
        });
    }

    @Test
    @WithMockUser ("user@user.com")
    public void  testarBuscarUsuarioPorIDCaminhoPositivo () throws Exception {
        Mockito.when(usuarioService.buscarUsuarioPorId(Mockito.anyString())).thenReturn(usuario);

        ResultActions resultadoEsperado = realizarRequisicao(null, 200, "GET", "/000aaa");

    }

    @Test
    @WithMockUser ("user@user.com")
    public void  testarBuscarUsuarioPorIDCaminhoNegativo () throws Exception {
        Mockito.doThrow(UsuarioNaoLocalizadoException.class).when(usuarioService).buscarUsuarioPorId(Mockito.anyString());

        ResultActions resultadoEsperado = realizarRequisicao(null, 404, "GET", "/teste");

    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarAtualizarDadosUsuarioCaminhoPositivo() throws Exception {
        Mockito.when(usuarioService.buscarUsuarioPorId(Mockito.anyString())).thenReturn(usuario);
        Mockito.when(usuarioService.atualizarDadosUsuario(Mockito.anyString(),Mockito.any(Usuario.class))).thenReturn(usuario);

        ResultActions resultadoEsperado = realizarRequisicao(usuario, 200, "PUT", "/dados/000aaa");

        String jsonResposta = resultadoEsperado.andReturn().getResponse().getContentAsString();
        UsuarioAtualizarDadosDTO usuarioAtualizarDadosDTO = objectMapper.readValue(jsonResposta, UsuarioAtualizarDadosDTO.class);
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarAtualizarDadosUsuarioCaminhoNegativo() throws Exception {
        Mockito.doThrow(UsuarioNaoLocalizadoException.class).when(usuarioService).atualizarDadosUsuario(
                Mockito.anyString(), Mockito.any(Usuario.class));

        ResultActions resultadoEsperado = realizarRequisicao(usuario, 404, "PUT", "/dados/teste");

    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarAtualizarDadosLoginUsuarioCaminhoPositivo() throws Exception {
        Mockito.when(usuarioService.buscarUsuarioPorId(Mockito.anyString())).thenReturn(usuario);
        Mockito.when(usuarioService.atualizarDadosLoginUsuario(Mockito.anyString(), Mockito.any(Usuario.class)))
                .thenReturn(usuario);
        ResultActions resultadoEsperado = realizarRequisicao(usuario, 200, "PUT", "/login/000aaa");

        String jsonResposta = resultadoEsperado.andReturn().getResponse().getContentAsString();
    }

    @Test
    @WithMockUser ("user@user.com")
    public void testarAtualizarDadosLoginUsuarioCaminhoNegativo() throws Exception {
        Mockito.doThrow(UsuarioNaoLocalizadoException.class).when(usuarioService).atualizarDadosLoginUsuario(
                Mockito.anyString(), Mockito.any(Usuario.class));

        ResultActions resultadoEsperado = realizarRequisicao(usuario, 404, "PUT", "/login/teste");

    }

    @Test
    @WithMockUser ("user@user.com")
    public void  testarDeletarUsuarioPorIDCaminhoPositivo () throws Exception {
        Mockito.doNothing().when(usuarioService).deletarUsuario(Mockito.anyString());

        ResultActions resultadoEsperado = realizarRequisicao(usuario, 204, "DELETE","/000aaa");

        Mockito.verify(usuarioService, Mockito.times(1)).deletarUsuario(Mockito.anyString());

    }

    @Test
    @WithMockUser ("user@user.com")
    public void  testarDeletarUsuarioPorIDCaminhoNegativo () throws Exception {
        Mockito.doThrow(UsuarioNaoLocalizadoException.class).when(usuarioService).deletarUsuario(Mockito.anyString());

        ResultActions resultadoEsperado = realizarRequisicao(null, 404, "DELETE", "/teste");

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
