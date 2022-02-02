package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail (String email);
    boolean existsByTelefone(String telefone);
    List<Usuario> findAllByGenero (Genero genero);
    List<Usuario> findAllByUf (String uf);
    List<Usuario> findAllByLocalidade(String localidade);
    List<Usuario> findAllByPreferenciaTemPet (Boolean temPet);
    List<Usuario> findAllByPreferenciaFumante (Boolean fumante);
    List<Usuario> findAllByPreferenciaTipoDePet (TipoDePet tipoDePet);

}
