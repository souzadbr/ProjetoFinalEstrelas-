package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UsuarioServiceTest {
    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Preferencia preferencia;

    @BeforeEach
    public void setup (){
        usuario = new Usuario();
        usuario.setId("000aaa");
        usuario.setNome("Usuario Teste");
        usuario.setEmail("usuario@usuario.com");
        usuario.setIdade(23);
        usuario.setTelefone("79999999999");
        usuario.setEstado(Estado.SERGIPE);
        usuario.setGenero(Genero.OUTRO);
        usuario.setSenha("senhateste");

        preferencia = new Preferencia();
        preferencia.setId(1);
        preferencia.setTemPet(true);
        preferencia.setTipoDePet(TipoDePet.OUTRO);
        preferencia.setFumante(false);
        preferencia.setDisponivelParaReceberUmZupper(true);
        preferencia.setConteAlgoQueNaoPerguntamos("Teste");

        usuario.setPreferencia(preferencia);
    }

    @Test
    public void testarSalvarUsuarioCaminhoPositivo(){
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        Mockito.when(usuarioService.verificarEmailExistente(Mockito.anyString())).thenReturn(false);
        Mockito.when(usuarioService.verificarTelefoneExistente(Mockito.anyString())).thenReturn(false);
        
        Usuario usuarioObjeto = usuarioService.salvarUsuario(usuario);
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));
    }
}
