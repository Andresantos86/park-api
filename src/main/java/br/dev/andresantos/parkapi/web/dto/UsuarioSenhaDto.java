package br.dev.andresantos.parkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioSenhaDto {

  @NotBlank(message = "O campo senhaAtual nao pode estar vazio")
  @Size(min=6, max = 6)
  private String senhaAtual;
  @NotBlank(message = "O campo novaSenha nao pode estar vazio")
  @Size(min=6, max = 6)
  private String novaSenha;
  @NotBlank(message = "O campo confirmaSenha nao pode estar vazio")
  @Size(min=6, max = 6)
  private String confirmaSenha;
}
