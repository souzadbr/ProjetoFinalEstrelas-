package br.com.zup.CouchZupper.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario novoUsuario){
        return usuarioRepository.save(novoUsuario);

    }
}
