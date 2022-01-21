package br.com.zup.CouchZupper.security.JWT;
import br.com.zup.LeadCollector.config.security.JWT.exceptions.AcessoNegadoException;
import br.com.zup.LeadCollector.config.security.UsuarioLogado;
import br.com.zup.LeadCollector.usuario.dtos.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class FiltroDeAutenticacaoJWT extends UsernamePasswordAuthenticationFilter{
    private JWTComponent jwtComponent;
    private AuthenticationManager authenticationManager;

    public FiltroDeAutenticacaoJWT(JWTComponent jwtComponent, AuthenticationManager authenticationManager){
        this.jwtComponent = jwtComponent;
        this.authenticationManager = authenticationManager;
    }

}
