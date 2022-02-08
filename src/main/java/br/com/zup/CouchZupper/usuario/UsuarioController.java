package br.com.zup.CouchZupper.usuario;

import br.com.zup.CouchZupper.enums.Genero;
import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.usuario.dtos.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@Api(value = "Couch Zupper")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ApiOperation(value = "Método responsável por cadastrar usuário")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarUsuario(@RequestBody @Valid UsuarioRequisicaoDTO usuarioRequisicaoDTO) throws Exception {
        Usuario usuario = modelMapper.map(usuarioRequisicaoDTO, Usuario.class);
        usuarioService.salvarUsuario(usuario);
    }

    @GetMapping
    @ApiOperation(value = "Método responsável por listar usuários de acordo com filtros")
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
    @ApiOperation(value = "Método responsável por exibir usuário por Id")
    public UsuarioRespostaDTO exibirUsuarioPorId(@PathVariable String id){
        UsuarioRespostaDTO usuarioRespostaDTO = modelMapper.map(usuarioService.buscarUsuarioPorId(id), UsuarioRespostaDTO.class);
        return usuarioRespostaDTO;
    }
    @GetMapping("/match/{uf}")
    @ApiOperation(value = "Método responsável por comparar o quanto esses usuários são compativeis")
    public List<ComparacaoUsuarioDTO> compararUsuarios (@PathVariable String uf) {
        List<Usuario> usuariosFiltrados = usuarioService.listarUsuariosPorUf(uf);
        List<ComparacaoUsuarioDTO> comparacaoUsuarioDTOList = new ArrayList<>();

        for (Usuario usuarioReferencia : usuariosFiltrados ){
            ComparacaoUsuarioDTO comparacaoUsuarioDTO = modelMapper.map(usuarioReferencia, ComparacaoUsuarioDTO.class);
            double quantidadeCombinacoes = usuarioService.compararUsuarios(
                    usuarioReferencia, usuarioService.buscarUsuarioLogado());
            double porcentagemMatch = usuarioService.calcularPorcentagemCombinacoes(quantidadeCombinacoes);
            double valorFormatado = Math.round(porcentagemMatch);

            comparacaoUsuarioDTO.setPorcentagemMatch(valorFormatado);
            if (usuarioReferencia != usuarioService.buscarUsuarioLogado()){
                comparacaoUsuarioDTOList.add(comparacaoUsuarioDTO);
            }

        }
        return comparacaoUsuarioDTOList;

    }

    @PutMapping("/dados/{id}")
    @ApiOperation(value = "Método responsável por atualizar informações pessoais do usuário")
    public UsuarioAtualizarDadosDTO atualizarDadosUsuario(@PathVariable String id, @Valid

                                                          @RequestBody UsuarioRequisicaoAtualizarDadosDTO usuarioRequisicaoAtualizarDadosDTO){
        UsuarioAtualizarDadosDTO usuarioAtualizarDadosDTO;
        Usuario usuario = modelMapper.map(usuarioRequisicaoAtualizarDadosDTO, Usuario.class);
        Usuario usuarioAtualizar = usuarioService.atualizarDadosUsuario(id, usuario);
        usuarioAtualizarDadosDTO = modelMapper.map(usuarioAtualizar, UsuarioAtualizarDadosDTO.class);

        return usuarioAtualizarDadosDTO;
    }

    @PutMapping ("/login/{id}")
    @ApiOperation(value = "Método responsável por atualizar informações de login do usuário")
    public void atualizarDadosDeLoginUsuario(@PathVariable String id,
                                             @RequestBody LoginDTO loginDTO){
        Usuario usuario = modelMapper.map(loginDTO, Usuario.class);
        usuarioService.atualizarDadosLoginUsuario(id, usuario);

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método responsável por deletar um usuario buscando por Id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario(@PathVariable String id){
        usuarioService.deletarUsuario(id);
    }
}
