package br.dev.andresantos.parkapi.web.controller;

import br.dev.andresantos.parkapi.jwt.JwtToken;
import br.dev.andresantos.parkapi.jwt.JwtUserDetailsService;
import br.dev.andresantos.parkapi.web.dto.UsuarioLoginDtoReq;
import br.dev.andresantos.parkapi.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AutenticaController {

  private final JwtUserDetailsService detailsService;
  private final AuthenticationManager authenticationManager;

  @PostMapping("/auth")
  public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDtoReq dto, HttpServletRequest request){
    log.info("Processo de autenticação pelo login {}", dto.getUsername());
    try {
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
      authenticationManager.authenticate(authenticationToken);
      JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());
    }catch (AuthenticationException ex){
      ex.printStackTrace();
      log.warn("Erro nas credenciais de acesso para o username {}", dto.getUsername());

    }
    return  ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
  }
}
