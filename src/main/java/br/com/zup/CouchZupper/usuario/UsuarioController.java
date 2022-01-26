package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.usuario.dtos.ResumoCadastroDTO;
import br.com.zup.CouchZupper.usuario.dtos.UsuarioAtualizarDadosDTO;
import br.com.zup.CouchZupper.usuario.dtos.UsuarioRequisicaoDTO;
import br.com.zup.CouchZupper.usuario.dtos.UsuarioRespostaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarUsuario(@RequestBody @Valid UsuarioRequisicaoDTO usuarioRequisicaoDTO){
        Usuario usuario = modelMapper.map(usuarioRequisicaoDTO, Usuario.class);
        usuarioService.salvarUsuario(usuario);
    }

    @GetMapping
    public List<ResumoCadastroDTO> buscarUsuarios(@RequestParam(required = false) @Valid Estado estado,
                                                  @RequestParam(required = false) Genero genero){
        List<ResumoCadastroDTO> listaResumo = new ArrayList<>();
        for (Usuario usuario: usuarioService.buscarUsuarios(estado, genero)){
            ResumoCadastroDTO resumo = modelMapper.map(usuario, ResumoCadastroDTO.class);
            listaResumo.add(resumo);
        }
        return listaResumo;
    }
    @GetMapping("/{id}")
    public UsuarioRespostaDTO exibirUsuarioPorId(@PathVariable String id){
        UsuarioRespostaDTO usuarioRespostaDTO = modelMapper.map(usuarioService.buscarUsuarioPorId(id), UsuarioRespostaDTO.class);
        return usuarioRespostaDTO;
    }

    //fazer 3 puts, 1 pra atualizar os dados , 1 para atualizar email e senha e outro as preferências
    @PutMapping("/dados/{id}")
    public UsuarioAtualizarDadosDTO atualizarDadosUsuario(@PathVariable String id, @Valid
                                                          @RequestBody UsuarioRequisicaoDTO usuarioRequisicaoDTO){
        UsuarioAtualizarDadosDTO usuarioAtualizarDadosDTO;
        Usuario usuarioAAtualizar = usuarioService.buscarUsuarioPorId(id);
        usuarioAAtualizar.setNome(usuarioRequisicaoDTO.getNome());
        usuarioAAtualizar.setTelefone(usuarioRequisicaoDTO.getTelefone());
        usuarioAAtualizar.setEstado(usuarioRequisicaoDTO.getEstado());
        usuarioAAtualizar.setGenero(usuarioRequisicaoDTO.getGenero());
        usuarioAAtualizar.setIdade(usuarioRequisicaoDTO.getIdade());
        usuarioAtualizarDadosDTO = modelMapper.map(usuarioService.atualizarDadosUsuario(usuarioAAtualizar), UsuarioAtualizarDadosDTO.class);

        return usuarioAtualizarDadosDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario(@PathVariable String id){
        usuarioService.deletarUsuario(id);
    }
}
