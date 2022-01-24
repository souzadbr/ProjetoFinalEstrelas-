package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.exception.EmailJaCadastradoException;
import br.com.zup.CouchZupper.exception.TelefoneJaCadastradoException;
import br.com.zup.CouchZupper.exception.UsuarioNaoLocalizadoException;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;
import java.util.List;

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

    @Test
    public void testarSalvarUsuarioEmailExistente(){
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        Mockito.when(usuarioService.verificarEmailExistente(Mockito.anyString())).thenReturn(true);
        Mockito.when(usuarioService.verificarTelefoneExistente(Mockito.anyString())).thenReturn(false);

        EmailJaCadastradoException exception = Assertions.assertThrows(EmailJaCadastradoException.class, () -> {
            usuarioService.salvarUsuario(usuario);
        });

        Mockito.verify(usuarioRepository, Mockito.times(0)).save(Mockito.any(Usuario.class));
    }

    @Test
    public void testarSalvarUsuarioTelefoneExistente(){
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        Mockito.when(usuarioService.verificarEmailExistente(Mockito.anyString())).thenReturn(false);
        Mockito.when(usuarioService.verificarTelefoneExistente(Mockito.anyString())).thenReturn(true);

        TelefoneJaCadastradoException exception = Assertions.assertThrows(TelefoneJaCadastradoException.class, () -> {
            usuarioService.salvarUsuario(usuario);
        });

        Mockito.verify(usuarioRepository, Mockito.times(0)).save(Mockito.any(Usuario.class));
    }

    @Test
    public void testarVerificarEmailExistente(){
        usuarioService.verificarEmailExistente(Mockito.anyString());
        Mockito.verify(usuarioRepository, Mockito.times(1))
                .existsByEmail(Mockito.anyString());
    }

    @Test
    public void testarVerificarTelefoneExistente(){
        usuarioService.verificarTelefoneExistente(Mockito.anyString());
        Mockito.verify(usuarioRepository, Mockito.times(1))
                .existsByTelefone(Mockito.anyString());
    }

    @Test
    public void testarAtualizarDados(){
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuarioService.atualizarDadosUsuario(usuario);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));
    }

    @Test
    public void testarDeletarUsuarioCaminhoPositivo(){
        Mockito.when(usuarioRepository.existsById(Mockito.anyString())).thenReturn(true);
        usuarioService.deletarUsuario("000aaa");

        Mockito.verify(usuarioRepository, Mockito.times(1)).deleteById(Mockito.anyString());
    }

    @Test
    public void testarDeletarUsuarioCaminhoNegativo(){
        Mockito.when(usuarioRepository.existsById(Mockito.anyString())).thenReturn(false);
        Mockito.doNothing().when(usuarioRepository).deleteById(Mockito.anyString());

        UsuarioNaoLocalizadoException exception = Assertions.assertThrows(UsuarioNaoLocalizadoException.class, () -> {
           usuarioService.deletarUsuario("teste");
        });

    }
}
