package br.dev.andresantos.parkapi.web.dto.estacionamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EstacionamentoCreateDto {

  @NotBlank
  @Size(min = 7, max = 8)
  private String placa;
  @NotBlank
  private String marca;
  @NotBlank
  private String modelo;
  @NotBlank
  private String cor;
  @NotBlank
  @CPF
  @Size(min = 11, max = 11)
  private String clienteCpf;

}
