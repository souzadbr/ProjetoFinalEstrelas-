package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.usuario.dtos.UsuarioRequisicaoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
