package br.com.zup.CouchZupper.configs;

import br.com.zup.CouchZupper.exception.AcessoNegadoException;
import br.com.zup.CouchZupper.exception.EmailJaCadastradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ErroDeValidacao> manipularErrosValidacao(MethodArgumentNotValidException exception){
        List<ErroDeValidacao>erros=new ArrayList<>();
        for (FieldError fielError: exception.getFieldErrors()) {
            ErroDeValidacao erroDeValidacao = new ErroDeValidacao(fielError.getField(), fielError.getDefaultMessage());
            erros.add(erroDeValidacao);
        }
        return erros;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularEnum(HttpMessageNotReadableException exception) {
        return new MensagemDeErro("Possuí erros de escrita.");
    }

    @ExceptionHandler(AcessoNegadoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularErrosDeAcessoNegado (AcessoNegadoException exception) {
        return new MensagemDeErro(exception.getLocalizedMessage());
    }


