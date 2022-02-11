package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.exception.*;
import br.com.zup.CouchZupper.preferencia.Preferencia;
import br.com.zup.CouchZupper.viacep.Endereco;
import br.com.zup.CouchZupper.viacep.EnderecoService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class UsuarioServiceTest {
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private EnderecoService enderecoService;

    @Autowired
    private UsuarioService usuarioService;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Usuario usuario, usuarioAAtualizar;
    private Preferencia preferencia;
    private Endereco endereco;

    @BeforeEach
    public void setup() {


        endereco = new Endereco();
        endereco.setUf("SP");
        endereco.setLocalidade("São Paulo");
        endereco.setCep("03240070");
        endereco.setErro(false);

        usuario = new Usuario();
        usuario.setId("000aaa");
        usuario.setNome("Usuario Teste");
        usuario.setEmail("usuario@zup.com.br");
        usuario.setDataNascimento(LocalDate.of(2000, 3, 7));
        usuario.setTelefone("79999999999");
        usuario.setCep("03240070");
        usuario.setLocalidade(endereco.getLocalidade());
        usuario.setUf(endereco.getUf());
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

        usuarioAAtualizar = usuario;

    }

    @Test
    public void testarBuscarUsuariosPorIdCaminhoPositivo() {
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Usuario usuarioOptional = usuarioService.buscarUsuarioPorId(Mockito.anyString());

        Assertions.assertEquals(usuario.getId(), usuarioOptional.getId());
    }

    @Test
    public void testarBuscarUsuariosPorIdCaminhoNegativo() {
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        UsuarioNaoLocalizadoException exception = Assertions.assertThrows(UsuarioNaoLocalizadoException.class,
                () -> {
                    usuarioService.buscarUsuarioPorId("teste");
                });
    }

    @Test
    public void testarSalvarUsuarioCaminhoPositivo() throws Exception {

        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        Mockito.when(usuarioService.verificarEmailExistente(Mockito.anyString())).thenReturn(false);
        Mockito.when(usuarioService.verificarTelefoneExistente(Mockito.anyString())).thenReturn(false);
        Mockito.when(enderecoService.buscarEnderecoPorCep("03240070")).thenReturn(endereco);
        endereco =  enderecoService.buscarEnderecoPorCep(usuario.getCep());
        usuario.setLocalidade(endereco.getLocalidade());
        usuario.setUf(endereco.getUf());

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
        Mockito.when(enderecoService.buscarEnderecoPorCep(Mockito.anyString())).thenReturn(endereco) ;
        Usuario usuarioAtualizado = usuarioService.atualizarDadosUsuario("000aaa", usuarioAAtualizar);

        Assertions.assertEquals(usuarioAtualizado.getNome(), usuarioAAtualizar.getNome());
        Assertions.assertEquals(usuarioAtualizado.getTelefone(), usuarioAAtualizar.getTelefone());
        Assertions.assertEquals(usuarioAtualizado.getLocalidade(), usuarioAAtualizar.getLocalidade());
        Assertions.assertEquals(usuarioAtualizado.getUf(), usuarioAAtualizar.getUf());
        Assertions.assertEquals(usuarioAtualizado.getGenero(), usuarioAAtualizar.getGenero());

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));

    }

    @Test
    public void testarAtualizarDadosUsuarioCaminhoNegativo() {
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        UsuarioNaoLocalizadoException exception = Assertions.assertThrows(UsuarioNaoLocalizadoException.class,
                () -> {
                    usuarioService.atualizarDadosUsuario("teste", Mockito.any(Usuario.class));
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

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));

    }

    @Test
    public void testarAtualizarDadosLoginUsuarioCaminhoNegativo() {
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        UsuarioNaoLocalizadoException exception = Assertions.assertThrows(UsuarioNaoLocalizadoException.class,
                () -> {
                    usuarioService.atualizarDadosLoginUsuario("teste", Mockito.any(Usuario.class));
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

    @Test
    public void testarEmailValidoZupCaminhoNegativo() throws Exception {
        EmailNaoZupException exception = Assertions.assertThrows(EmailNaoZupException.class, () -> {
            usuarioService.validarEmailZup("teste@teste");
        });
    }

    @Test
    public void testarEmailValidoZupCaminhoPosito() throws Exception {
        usuarioService.validarEmailZup("teste@zup.com.br");
    }


    @Test
    public void testarCalculoDeIdade(){
        int idade = usuarioService.calcularIdade(usuario.getDataNascimento());
        Assert.assertEquals(idade,21);

    }

    @Test
    public void testarIdadeMinimaDeCadastro() throws Exception {
        usuario.setDataNascimento(LocalDate.of(2020,02,01));

        MenorDeIdadeException exception = Assertions.assertThrows(MenorDeIdadeException.class, () -> {
            usuarioService.salvarUsuario(usuario);
        });
    }

    @Test
    public void testarListarUsuarioPorUf(){
        Mockito.when(usuarioRepository.findAllByUf(Mockito.anyString())).thenReturn(List.of(usuario));
        usuarioService.listarUsuariosPorUf(usuario.getUf());

        Mockito.verify(usuarioRepository, Mockito.times(1)).findAllByUf(Mockito.anyString());
    }
}
