package br.dev.andresantos.parkapi.web.dto.vaga;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VagaResponseDto {
  private Long id;
  private String codigo;
  private String status;
}
