package br.dev.andresantos.parkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDtoReq {
  @NotBlank(message = "O campo e-mail nao pode estar vazio")
  @Email(message = "O formato do email está inválido", regexp = "^[a-z0-9.+-]+@[a-z0-9.+-]+\\.[a-z]{2,}$")
  private String username;

  @NotBlank(message = "O campo password nao pode estar vazio")
  @Size(min=6, max = 6)
  private String password;
}
