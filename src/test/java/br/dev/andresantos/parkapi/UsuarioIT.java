package br.dev.andresantos.parkapi;

import br.dev.andresantos.parkapi.web.dto.UsuarioDtoReq;
import br.dev.andresantos.parkapi.web.dto.UsuarioDtoRes;
import br.dev.andresantos.parkapi.web.dto.UsuarioSenhaDto;
import br.dev.andresantos.parkapi.web.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

  @Autowired
  WebTestClient testClient;

  @Test
  public void createUsuario_comUsernamePasswordValido_RetornaUsuarioComStatus201(){
    UsuarioDtoRes usuarioRes = testClient.post()
            .uri("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioDtoReq("quatro@email.com","444444"))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(UsuarioDtoRes.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(usuarioRes).isNotNull();
    Assertions.assertThat(usuarioRes.getId()).isNotNull();
    Assertions.assertThat(usuarioRes.getUsername()).isEqualTo("quatro@email.com");
    Assertions.assertThat(usuarioRes.getRole()).isEqualTo("CLIENTE");
  }

  @Test
  public void createUsuario_comUsernamePasswordInvalido_RetornaUsuarioComStatus422(){
    ErrorMessage responseBody = testClient.post()
            .uri("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioDtoReq("","444444"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    responseBody = testClient.post()
            .uri("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioDtoReq("tod@","444444"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    responseBody = testClient.post()
            .uri("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioDtoReq("tody@email.","444444"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
  }

  @Test
  public void passwordInvalido_createUsuario_RetornaUsuarioComStatus422(){
    ErrorMessage responseBody = testClient.post()
            .uri("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioDtoReq("quatro@email.com",""))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    responseBody = testClient.post()
            .uri("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioDtoReq("tod@","44444"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    responseBody = testClient.post()
            .uri("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioDtoReq("tody@email.","4444444"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
  }

  @Test
  public void userNameRepetido_createUsuario_RetornaErro409(){
    ErrorMessage resposeBody = testClient.post()
            .uri("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioDtoReq("um@email.com","444444"))
            .exchange()
            .expectStatus().isEqualTo(409)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(resposeBody).isNotNull();
    Assertions.assertThat(resposeBody.getStatus()).isEqualTo(409);
  }

  @Test
  public void buscarUsuario_existente_retornoUsuarioComStatus200(){
    UsuarioDtoRes resposeBody = testClient
            .get()
            .uri("/api/v1/usuarios/100")
            .exchange()
            .expectStatus().isOk()
            .expectBody(UsuarioDtoRes.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(resposeBody).isNotNull();
    Assertions.assertThat(resposeBody.getId()).isEqualTo(100);
    Assertions.assertThat(resposeBody.getUsername()).isEqualTo("um@email.com");
    Assertions.assertThat(resposeBody.getRole()).isEqualTo("ADMIN");
  }

  @Test
  public void buscarUsuario_naoExistente_retornoErrorMessageComStatus404(){
    ErrorMessage resposeBody = testClient
            .get()
            .uri("/api/v1/usuarios/80")
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(resposeBody).isNotNull();
    Assertions.assertThat(resposeBody.getStatus()).isEqualTo(404);
  }


  @Test
  public void editarSenha_dadosValidos_RetornaStatus204(){
    testClient.patch()
            .uri("/api/v1/usuarios/100")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDto("111111","101010","101010"))
            .exchange()
            .expectStatus().isNoContent();   }

  @Test
  public void editarSenha_idNaoExistente_retornoErrorMessageComStatus404(){
    ErrorMessage resposeBody = testClient
            .patch()
            .uri("/api/v1/usuarios/80")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDto("111111","101010","101010"))
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(resposeBody).isNotNull();
    Assertions.assertThat(resposeBody.getStatus()).isEqualTo(404);
  }

  @Test
  public void editarSenha_CamposInvalidos_retornoErrorMessageComStatus422(){
    ErrorMessage resposeBody = testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDto("","",""))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(resposeBody).isNotNull();
    Assertions.assertThat(resposeBody.getStatus()).isEqualTo(422);

     resposeBody = testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDto("11111","12121","12121"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(resposeBody).isNotNull();
    Assertions.assertThat(resposeBody.getStatus()).isEqualTo(422);

     resposeBody = testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDto("1111111","1212121","1212121"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(resposeBody).isNotNull();
    Assertions.assertThat(resposeBody.getStatus()).isEqualTo(422);
  }

  @Test
  public void editarSenha_senhaInvalida_retornoErrorMessageComStatus400(){
    ErrorMessage resposeBody = testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDto("111111","123456","123123"))
            .exchange()
            .expectStatus().isEqualTo(400)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(resposeBody).isNotNull();
    Assertions.assertThat(resposeBody.getStatus()).isEqualTo(400);

    resposeBody = testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDto("111112","123123","123123"))
            .exchange()
            .expectStatus().isEqualTo(400)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(resposeBody).isNotNull();
    Assertions.assertThat(resposeBody.getStatus()).isEqualTo(400);
  }

  @Test
  public void consultarUsuarios_retornoStatus200(){
    List<UsuarioDtoRes> resposeBody = testClient
            .get()
            .uri("/api/v1/usuarios")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(UsuarioDtoRes.class)
            .returnResult().getResponseBody();

    Assertions.assertThat(resposeBody).isNotNull();
    Assertions.assertThat(resposeBody.size()).isEqualTo(3);
  }

}