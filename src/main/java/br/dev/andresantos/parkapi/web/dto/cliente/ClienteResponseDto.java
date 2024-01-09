package br.dev.andresantos.parkapi.web.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteResponseDto {

  private  Long id;
  private String nome;
  private String cpf;
}
