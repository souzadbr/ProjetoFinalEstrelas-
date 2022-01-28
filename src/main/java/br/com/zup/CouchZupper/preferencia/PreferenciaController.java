package br.com.zup.CouchZupper.preferencia;

import br.com.zup.CouchZupper.enums.TipoDePet;
import br.com.zup.CouchZupper.preferencia.dtos.PreferenciaEntradaDTO;
import br.com.zup.CouchZupper.usuario.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/preferencias")
public class PreferenciaController {
    @Autowired
    private PreferenciaService preferenciaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<PreferenciaEntradaDTO> buscarPreferencias (@RequestParam(required = false) Boolean temPet,
                                                           @RequestParam(required = false) TipoDePet tipoDePet,
                                                           @RequestParam(required = false) Boolean fumante,
                                                           @RequestParam(required = false) Boolean disponivel,
                                                           @RequestParam(required = false) Usuario usuario) {
        List<PreferenciaEntradaDTO> listaResumo = new ArrayList<>();
        for(Preferencia preferencia : preferenciaService.buscarPreferencias(temPet,tipoDePet, fumante,
                disponivel, usuario)) {
            PreferenciaEntradaDTO resumo = modelMapper.map(preferencia, PreferenciaEntradaDTO.class);
            listaResumo.add(resumo);
        }
        return listaResumo;
    }

    @PutMapping ("/{id}")
    public void atualizarPreferencia(@PathVariable int id,
                                     @RequestBody PreferenciaEntradaDTO preferenciaEntradaDTO){
        Preferencia preferencia = modelMapper.map(preferenciaEntradaDTO, Preferencia.class);
        preferenciaService.atualizarPreferencias(id, preferencia);
    }

}
