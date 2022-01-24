package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.usuario.dtos.ResumoCadastroDTO;
import br.com.zup.CouchZupper.usuario.dtos.UsuarioRequisicaoDTO;
import br.com.zup.CouchZupper.usuario.dtos.UsuarioRespostaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void cadastrarUsuario(@RequestBody UsuarioRequisicaoDTO usuarioRequisicaoDTO){
        Usuario usuario = modelMapper.map(usuarioRequisicaoDTO, Usuario.class);
        usuarioService.salvarUsuario(usuario);
    }

    @GetMapping
    public List<ResumoCadastroDTO> buscarUsuarios(@RequestParam(required = false) Estado estado){
        List<ResumoCadastroDTO> listaResumo = new ArrayList<>();
        for (Usuario usuario: usuarioService.buscarUsuarios(estado)){
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
}
