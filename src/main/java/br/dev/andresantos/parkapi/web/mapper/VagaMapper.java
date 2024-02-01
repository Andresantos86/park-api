package br.dev.andresantos.parkapi.web.mapper;

import br.dev.andresantos.parkapi.entity.Vaga;
import br.dev.andresantos.parkapi.web.dto.vaga.VagaCreateDto;
import br.dev.andresantos.parkapi.web.dto.vaga.VagaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {
  public static Vaga toVaga (VagaCreateDto dto){
    return new ModelMapper().map(dto, Vaga.class);
  }

  public static VagaResponseDto toDto(Vaga vaga){
    return  new ModelMapper().map(vaga, VagaResponseDto.class);
  }
}
