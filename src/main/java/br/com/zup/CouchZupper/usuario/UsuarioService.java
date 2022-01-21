package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.exception.EmailJaCadastradoException;
import br.com.zup.CouchZupper.exception.TelefoneJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
