package br.com.zup.CouchZupper.configs;

import br.com.zup.CouchZupper.exception.*;
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
    public List<ErroDeValidacao> manipularErrosValidacao(MethodArgumentNotValidException exception) {
        List<ErroDeValidacao> erros = new ArrayList<>();
        for (FieldError fielError : exception.getFieldErrors()) {
            ErroDeValidacao erroDeValidacao = new ErroDeValidacao(fielError.getField(), fielError.getDefaultMessage());
            erros.add(erroDeValidacao);
        }
        return erros;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularEnum(HttpMessageNotReadableException exception) {

        return new MensagemDeErro("Dado inserido incorretamente, verifique e tente novamente");

    }

    @ExceptionHandler(AcessoNegadoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularErrosDeAcessoNegado(AcessoNegadoException exception) {
        return new MensagemDeErro("Verifique credenciais de acesso");
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularEmailsJaCadastrados(EmailJaCadastradoException exception) {
        return new MensagemDeErro("Esse e-mail já foi cadastrado");
    }

    @ExceptionHandler(TelefoneJaCadastradoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularTelefoneJaCadastrados(TelefoneJaCadastradoException exception) {
        return new MensagemDeErro("Esse telefone já foi cadastrado");
    }

    @ExceptionHandler(TokenInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularTokenInvalido(TokenInvalidoException exception) {
        return new MensagemDeErro("Verifique o token de autenticação");
    }

    @ExceptionHandler(UsuarioNaoLocalizadoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro manipularUsuarioNaoLocalizado(UsuarioNaoLocalizadoException exception){
        return new MensagemDeErro("Cadastro não encontrado");
    }

    @ExceptionHandler(PreferenciaNaoLocalizadaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro manipularPreferenciaNaoLocalizada(PreferenciaNaoLocalizadaException exception){
        return new MensagemDeErro(exception.getLocalizedMessage());
    }

    @ExceptionHandler(EmailNaoZupException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularEmailNaoZup(EmailNaoZupException exception){
        return new MensagemDeErro("E-mail inválido, use um email da Zup!");
    }

    @ExceptionHandler(MenorDeIdadeException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularIdadeDoZupper(MenorDeIdadeException exception){
        return new MensagemDeErro("Menos de 18 anos não pode se cadastrar!");
    }


}