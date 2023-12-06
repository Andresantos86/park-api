package br.dev.andresantos.parkapi.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDtoRes {
  private Long id;
  private String username;
  private String role;
}
