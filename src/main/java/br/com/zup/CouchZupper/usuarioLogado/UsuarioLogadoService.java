package br.com.zup.CouchZupper.usuarioLogado;

import br.com.zup.CouchZupper.security.UsuarioLogado;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogadoService {
    public String pegarId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UsuarioLogado usuarioLogado = (UsuarioLogado) principal;
        return usuarioLogado.getId();
    }
}
