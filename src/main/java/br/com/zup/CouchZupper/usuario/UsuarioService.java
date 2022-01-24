package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.exception.EmailJaCadastradoException;
import br.com.zup.CouchZupper.exception.TelefoneJaCadastradoException;
import br.com.zup.CouchZupper.exception.UsuarioNaoLocalizadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario novoUsuario){
        if(verificarEmailExistente(novoUsuario.getEmail())){
            throw new EmailJaCadastradoException();
        }
        if(  verificarTelefoneExistente(novoUsuario.getTelefone())){
            throw new TelefoneJaCadastradoException();
        }
        return usuarioRepository.save(novoUsuario);

    }

    public boolean verificarEmailExistente (String email){
        return usuarioRepository.existsByEmail(email);
    }

    public boolean verificarTelefoneExistente (String telefone){
        return usuarioRepository.existsByTelefone(telefone);
    }

    public List<Usuario> listarUsuarios(){
        Iterable<Usuario> listaUsuarios = usuarioRepository.findAll();
        return (List<Usuario>) listaUsuarios;
    }

    public List<Usuario> buscarUsuarios (Estado estado){
        if (estado != null){
            return usuarioRepository.findAllByEstado(estado);
        }

        return listarUsuarios();
    }

    public Usuario buscarUsuarioPorId(String id){
        for (Usuario usuarioReferencia : usuarioRepository.findAll()){
            if (id.equals(usuarioReferencia.getId())){
                return usuarioReferencia;
            }
        }
        throw new UsuarioNaoLocalizadoException();
    }

    public void deletarUsuario (String id){
        if (!usuarioRepository.existsById(id)){
            throw new UsuarioNaoLocalizadoException();
        } else {
            usuarioRepository.deleteById(id);
        }
    }
}
