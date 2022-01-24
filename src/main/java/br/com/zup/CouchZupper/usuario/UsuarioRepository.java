package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Estado;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    boolean existsByEmail (String email);
    boolean existsByTelefone(String telefone);
    List<Usuario> findAllByEstado (Estado estado);
}
