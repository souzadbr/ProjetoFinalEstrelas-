package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.exception.EmailJaCadastradoException;
import br.com.zup.CouchZupper.exception.EmailNaoZupException;
import br.com.zup.CouchZupper.exception.TelefoneJaCadastradoException;
import br.com.zup.CouchZupper.exception.UsuarioNaoLocalizadoException;
import br.com.zup.CouchZupper.usuario.dtos.UsuarioRequisicaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public Usuario salvarUsuario(Usuario novoUsuario) throws Exception {

        String senhaEscondida = bCryptPasswordEncoder.encode(novoUsuario.getSenha());

        novoUsuario.setSenha(senhaEscondida);

        if (verificarEmailExistente(novoUsuario.getEmail())) {
            throw new EmailJaCadastradoException();
        }
        if(validarEmailZup(novoUsuario.getEmail())){
            throw new EmailNaoZupException();
        }
        if (verificarTelefoneExistente(novoUsuario.getTelefone())) {
            throw new TelefoneJaCadastradoException();
        }
        return usuarioRepository.save(novoUsuario);

    }

    public boolean verificarEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public boolean verificarTelefoneExistente(String telefone) {
        return usuarioRepository.existsByTelefone(telefone);
    }

    public List<Usuario> listarUsuarios() {
        Iterable<Usuario> listaUsuarios = usuarioRepository.findAll();
        return (List<Usuario>) listaUsuarios;
    }

    public List<Usuario> buscarUsuarios(Estado estado, Genero genero) {
        if (estado != null) {
            return usuarioRepository.findAllByEstado(estado);
        }
        if (genero != null) {
            return usuarioRepository.findAllByGenero(genero);
        }

        return listarUsuarios();
    }

    public Usuario buscarUsuarioPorId(String id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty()){
            throw new UsuarioNaoLocalizadoException();
        }

        return usuarioOptional.get();
    }

    public Usuario atualizarDadosUsuario(String id, Usuario usuario) {

        Usuario usuarioAAtualizar = buscarUsuarioPorId(id);

        usuarioAAtualizar.setNome(usuario.getNome());
        usuarioAAtualizar.setTelefone(usuario.getTelefone());
        usuarioAAtualizar.setEstado(usuario.getEstado());
        usuarioAAtualizar.setGenero(usuario.getGenero());

        usuarioRepository.save(usuarioAAtualizar);

        return usuarioAAtualizar;
    }

    public Usuario atualizarDadosLoginUsuario(String id, Usuario usuario) {

        Usuario usuarioAAtualizar = buscarUsuarioPorId(id);
        String senhaEscondida = bCryptPasswordEncoder.encode(usuario.getSenha());

        usuarioAAtualizar.setEmail(usuario.getEmail());
        usuarioAAtualizar.setSenha(senhaEscondida);

        usuarioRepository.save(usuarioAAtualizar);

        return usuarioAAtualizar;
    }


    public void deletarUsuario(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNaoLocalizadoException();
        } else {
            usuarioRepository.deleteById(id);
        }
    }

    public boolean validarEmailZup(String email)throws Exception{
        if(!email.contains("@zup.com.br")){
            throw new EmailNaoZupException();
        }
        return false;
    }

}
