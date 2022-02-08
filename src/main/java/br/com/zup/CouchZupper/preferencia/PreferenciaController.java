package br.com.zup.CouchZupper.preferencia;

import br.com.zup.CouchZupper.preferencia.dtos.PreferenciaEntradaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferencias")
@Api(value = "Couch Zupper")
@CrossOrigin(origins = "*")
public class PreferenciaController {
    @Autowired
    private PreferenciaService preferenciaService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping("/{id}")
    @ApiOperation(value = "Método responsável por atualizar as preferencias")
    public void atualizarPreferencia(@PathVariable int id,
                                     @RequestBody PreferenciaEntradaDTO preferenciaEntradaDTO) {
        Preferencia preferencia = modelMapper.map(preferenciaEntradaDTO, Preferencia.class);
        preferenciaService.atualizarPreferencias(id, preferencia);
    }

}
