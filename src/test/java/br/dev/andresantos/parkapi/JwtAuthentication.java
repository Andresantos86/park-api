package br.dev.andresantos.parkapi;

import br.dev.andresantos.parkapi.jwt.JwtToken;
import br.dev.andresantos.parkapi.web.dto.UsuarioLoginDtoReq;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAuthentication {
  public static Consumer<HttpHeaders> getHeaderuthorization(WebTestClient client, String username, String password){
    String token = client
            .post()
            .uri("api/v1/auth")
            .bodyValue( new UsuarioLoginDtoReq(username, password))
            .exchange()
            .expectStatus().isOk()
            .expectBody(JwtToken.class)
            .returnResult().getResponseBody().getToken();
    return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
  }
}
