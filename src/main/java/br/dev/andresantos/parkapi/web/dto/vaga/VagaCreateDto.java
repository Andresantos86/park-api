package br.dev.andresantos.parkapi.web.dto.vaga;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VagaCreateDto {

  @NotBlank
  @Size(min = 4, max = 4)
  private String codigo;

  @NotBlank
  @Pattern(regexp = "LIVRE|OCUPADA")
  private String status;
}
