package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.exception.EmailJaCadastradoException;
import br.com.zup.CouchZupper.exception.TelefoneJaCadastradoException;
import br.com.zup.CouchZupper.exception.UsuarioNaoLocalizadoException;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UsuarioServiceTest {
    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Usuario usuario, usuarioAAtualizar;
    private Preferencia preferencia;

    @BeforeEach
    public void setup() {
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

        usuarioAAtualizar = new Usuario();
    }

    @Test
    public void testarBuscarUsuariosPorIdCaminhoPositivo(){
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Usuario usuarioOptional = usuarioService.buscarUsuarioPorId(Mockito.anyString());

        Assertions.assertEquals(usuario.getId(), usuarioOptional.getId());
    }

    @Test
    public void testarBuscarUsuariosPorIdCaminhoNegativo(){
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        UsuarioNaoLocalizadoException exception = Assertions.assertThrows(UsuarioNaoLocalizadoException.class,
                () -> {usuarioService.buscarUsuarioPorId("teste");
        });
    }

    @Test
    public void testarSalvarUsuarioCaminhoPositivo() {
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        Mockito.when(usuarioService.verificarEmailExistente(Mockito.anyString())).thenReturn(false);
        Mockito.when(usuarioService.verificarTelefoneExistente(Mockito.anyString())).thenReturn(false);

        usuarioService.salvarUsuario(usuario);
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));
    }

    @Test
    public void testarSalvarUsuarioEmailExistente() {
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        Mockito.when(usuarioService.verificarEmailExistente(Mockito.anyString())).thenReturn(true);
        Mockito.when(usuarioService.verificarTelefoneExistente(Mockito.anyString())).thenReturn(false);

        EmailJaCadastradoException exception = Assertions.assertThrows(EmailJaCadastradoException.class, () -> {
            usuarioService.salvarUsuario(usuario);
        });

        Mockito.verify(usuarioRepository, Mockito.times(0)).save(Mockito.any(Usuario.class));
    }

    @Test
    public void testarSalvarUsuarioTelefoneExistente() {
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        Mockito.when(usuarioService.verificarEmailExistente(Mockito.anyString())).thenReturn(false);
        Mockito.when(usuarioService.verificarTelefoneExistente(Mockito.anyString())).thenReturn(true);

        TelefoneJaCadastradoException exception = Assertions.assertThrows(TelefoneJaCadastradoException.class, () -> {
            usuarioService.salvarUsuario(usuario);
        });

        Mockito.verify(usuarioRepository, Mockito.times(0)).save(Mockito.any(Usuario.class));
    }

    @Test
    public void testarVerificarEmailExistente() {
        usuarioService.verificarEmailExistente(Mockito.anyString());
        Mockito.verify(usuarioRepository, Mockito.times(1))
                .existsByEmail(Mockito.anyString());
    }

    @Test
    public void testarVerificarTelefoneExistente() {
        usuarioService.verificarTelefoneExistente(Mockito.anyString());
        Mockito.verify(usuarioRepository, Mockito.times(1))
                .existsByTelefone(Mockito.anyString());
    }

    @Test
    public void testarAtualizarDadosUsuarioCaminhoPositivo() {
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        usuarioService.atualizarDadosUsuario(Mockito.anyString(), usuarioAAtualizar);

        Assertions.assertEquals(usuario.getNome(), usuarioAAtualizar.getNome());
        Assertions.assertEquals(usuario.getTelefone(), usuarioAAtualizar.getTelefone());
        Assertions.assertEquals(usuario.getEstado(), usuarioAAtualizar.getEstado());
        Assertions.assertEquals(usuario.getGenero(), usuarioAAtualizar.getGenero());

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));

    }

    @Test
    public void testarAtualizarDadosUsuarioCaminhoNegativo(){
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        UsuarioNaoLocalizadoException exception = Assertions.assertThrows(UsuarioNaoLocalizadoException.class,
                () -> {usuarioService.atualizarDadosUsuario("teste", Mockito.any(Usuario.class));
                });

        Mockito.verify(usuarioRepository, Mockito.times(0)).save(Mockito.any(Usuario.class));

    }
    @Test
    public void testarAtualizarLoginUsuarioCaminhoPositivo() {
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn(usuario.getSenha());
        usuarioService.atualizarDadosLoginUsuario(Mockito.anyString(), usuarioAAtualizar);

        Assertions.assertEquals(usuario.getEmail(), usuarioAAtualizar.getEmail());
        //Assertions.assertEquals(usuario.getSenha(), usuarioAAtualizar.getSenha());

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));

    }

    @Test
    public void testarAtualizarDadosLoginUsuarioCaminhoNegativo(){
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        UsuarioNaoLocalizadoException exception = Assertions.assertThrows(UsuarioNaoLocalizadoException.class,
                () -> {usuarioService.atualizarDadosLoginUsuario("teste", Mockito.any(Usuario.class));
                });

        Mockito.verify(usuarioRepository, Mockito.times(0)).save(Mockito.any(Usuario.class));

    }

    @Test
    public void testarDeletarUsuarioCaminhoPositivo() {
        Mockito.when(usuarioRepository.existsById(Mockito.anyString())).thenReturn(true);
        usuarioService.deletarUsuario("000aaa");

        Mockito.verify(usuarioRepository, Mockito.times(1)).deleteById(Mockito.anyString());
    }

    @Test
    public void testarDeletarUsuarioCaminhoNegativo() {
        Mockito.when(usuarioRepository.existsById(Mockito.anyString())).thenReturn(false);
        Mockito.doNothing().when(usuarioRepository).deleteById(Mockito.anyString());

        UsuarioNaoLocalizadoException exception = Assertions.assertThrows(UsuarioNaoLocalizadoException.class, () -> {
            usuarioService.deletarUsuario("teste");
        });

    }
}
