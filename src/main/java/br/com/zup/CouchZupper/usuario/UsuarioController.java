package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Estado;
import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.usuario.dtos.*;
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
    public void cadastrarUsuario(@RequestBody @Valid UsuarioRequisicaoDTO usuarioRequisicaoDTO) throws Exception {
        Usuario usuario = modelMapper.map(usuarioRequisicaoDTO, Usuario.class);
        usuarioService.salvarUsuario(usuario);
    }

    @GetMapping
    public List<ResumoCadastroDTO> buscarUsuarios(@RequestParam(required = false) @Valid String uf,
                                                  @RequestParam(required = false)String localidade,
                                                  @RequestParam(required = false) Genero genero,
                                                  @RequestParam (required = false) Boolean temPet,
                                                  @RequestParam (required = false) Boolean fumante,
                                                  @RequestParam (required = false) TipoDePet tipoDePet){
        List<ResumoCadastroDTO> listaResumo = new ArrayList<>();
        for (Usuario usuario: usuarioService.buscarUsuarios(uf, localidade, genero, temPet, fumante, tipoDePet)){
            if (usuario.getPreferencia().isDisponivelParaReceberUmZupper()){
                ResumoCadastroDTO resumo = modelMapper.map(usuario, ResumoCadastroDTO.class);
                resumo.setIdade(usuarioService.calcularIdade(usuario.getDataNascimento()));
                listaResumo.add(resumo);
            }

        }
        return listaResumo;
    }
    @GetMapping("/{id}")
    public UsuarioRespostaDTO exibirUsuarioPorId(@PathVariable String id){
        UsuarioRespostaDTO usuarioRespostaDTO = modelMapper.map(usuarioService.buscarUsuarioPorId(id), UsuarioRespostaDTO.class);
        return usuarioRespostaDTO;
    }

    @PutMapping("/dados/{id}")
    public UsuarioAtualizarDadosDTO atualizarDadosUsuario(@PathVariable String id, @Valid

                                                          @RequestBody UsuarioRequisicaoDTO usuarioRequisicaoDTO){
        UsuarioAtualizarDadosDTO usuarioAtualizarDadosDTO;
        Usuario usuario = modelMapper.map(usuarioRequisicaoDTO, Usuario.class);
        Usuario usuarioAtualizar = usuarioService.atualizarDadosUsuario(id, usuario);
        usuarioAtualizarDadosDTO = modelMapper.map(usuarioAtualizar, UsuarioAtualizarDadosDTO.class);

        return usuarioAtualizarDadosDTO;
    }

    @PutMapping ("/login/{id}")
    public void atualizarDadosDeLoginUsuario(@PathVariable String id,
                                             @RequestBody LoginDTO loginDTO){
        Usuario usuario = modelMapper.map(loginDTO, Usuario.class);
        usuarioService.atualizarDadosLoginUsuario(id, usuario);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario(@PathVariable String id){
        usuarioService.deletarUsuario(id);
    }
}
