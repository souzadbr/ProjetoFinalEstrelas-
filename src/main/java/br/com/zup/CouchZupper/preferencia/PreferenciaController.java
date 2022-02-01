package br.com.zup.CouchZupper.preferencia;

import br.com.zup.CouchZupper.preferencia.dtos.PreferenciaEntradaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferencias")
public class PreferenciaController {
    @Autowired
    private PreferenciaService preferenciaService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping("/{id}")
    public void atualizarPreferencia(@PathVariable int id,
                                     @RequestBody PreferenciaEntradaDTO preferenciaEntradaDTO) {
        Preferencia preferencia = modelMapper.map(preferenciaEntradaDTO, Preferencia.class);
        preferenciaService.atualizarPreferencias(id, preferencia);
    }

}
