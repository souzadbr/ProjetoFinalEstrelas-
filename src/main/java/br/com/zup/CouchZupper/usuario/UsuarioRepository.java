package br.com.zup.CouchZupper.usuario;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    boolean existsByEmail (String email);
    boolean existsByTelefone(String telefone);
}
