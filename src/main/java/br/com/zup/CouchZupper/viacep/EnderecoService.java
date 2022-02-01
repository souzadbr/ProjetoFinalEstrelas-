package br.com.zup.CouchZupper.viacep;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface EnderecoService {

    @GetMapping("{cep}/json")
    Endereco buscarEnderecoPorCep(@PathVariable String cep);
}
