package br.com.zup.CouchZupper.security;

import br.com.zup.CouchZupper.usuario.Usuario;
import br.com.zup.CouchZupper.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UsuarioLoginService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);

        optionalUsuario.orElseThrow(() -> new UsernameNotFoundException("Dados de login inseridos incorretamente"));
        Usuario usuario = optionalUsuario.get();

        return new UsuarioLogado(usuario.getId(), usuario.getEmail(), usuario.getSenha());

    }
}
