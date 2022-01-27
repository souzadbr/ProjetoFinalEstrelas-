package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail (String email);
    boolean existsByTelefone(String telefone);
    List<Usuario> findAllByEstado (Estado estado);
    List<Usuario> findAllByGenero (Genero genero);

}
