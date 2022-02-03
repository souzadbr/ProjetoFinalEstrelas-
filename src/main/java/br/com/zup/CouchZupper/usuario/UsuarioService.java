package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.exception.EmailJaCadastradoException;
import br.com.zup.CouchZupper.exception.EmailNaoZupException;
import br.com.zup.CouchZupper.exception.TelefoneJaCadastradoException;
import br.com.zup.CouchZupper.exception.UsuarioNaoLocalizadoException;
import br.com.zup.CouchZupper.exception.MenorDeIdadeException;
import br.com.zup.CouchZupper.usuario.dtos.UsuarioRequisicaoDTO;
import br.com.zup.CouchZupper.viacep.Endereco;
import br.com.zup.CouchZupper.viacep.EnderecoService;
import br.com.zup.CouchZupper.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EnderecoService enderecoService;


    public Usuario salvarUsuario(Usuario novoUsuario) throws Exception {

        String senhaEscondida = bCryptPasswordEncoder.encode(novoUsuario.getSenha());

        novoUsuario.setSenha(senhaEscondida);

        if (verificarEmailExistente(novoUsuario.getEmail())) {
            throw new EmailJaCadastradoException();
        }
        if(validarEmailZup(novoUsuario.getEmail())){
            throw new EmailNaoZupException();
        }
        if(calcularIdade(novoUsuario.getDataNascimento())<18){
            throw new MenorDeIdadeException();
        }
        if (verificarTelefoneExistente(novoUsuario.getTelefone())) {
            throw new TelefoneJaCadastradoException();
        } else {
            Endereco endereco = enderecoService.buscarEnderecoPorCep(novoUsuario.getCep());
            if(endereco.getErro()){
                throw new CepInvalidoException();
            }
            novoUsuario.setUf(endereco.getUf());
            novoUsuario.setLocalidade(endereco.getLocalidade());
            return usuarioRepository.save(novoUsuario);
        }

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

    public List<Usuario> buscarUsuarios(String uf, String localidade, Genero genero, Boolean temPet, Boolean fumante, TipoDePet tipoDePet) {
        if (uf != null) {
            return usuarioRepository.findAllByUf(uf);
        }
        if (localidade != null) {
            return usuarioRepository.findAllByLocalidade(localidade);
        }
        if (genero != null) {
            return usuarioRepository.findAllByGenero(genero);
        }
        if (temPet != null){
            return usuarioRepository.findAllByPreferenciaTemPet(temPet);
        }
        if (fumante != null){
            return usuarioRepository.findAllByPreferenciaFumante(fumante);
        }
        if (tipoDePet != null){
            return usuarioRepository.findAllByPreferenciaTipoDePet(tipoDePet);
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

        Endereco endereco = enderecoService.buscarEnderecoPorCep(usuario.getCep());

        usuarioAAtualizar.setNome(usuario.getNome());
        usuarioAAtualizar.setTelefone(usuario.getTelefone());
        usuarioAAtualizar.setUf(endereco.getUf());
        usuarioAAtualizar.setLocalidade(endereco.getLocalidade());
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

    public int calcularIdade(LocalDate dataNascimento){
        LocalDate dataAtual = LocalDate.now();
        int idade = Period.between(dataNascimento, dataAtual).getYears();
        return idade;
    }

}
