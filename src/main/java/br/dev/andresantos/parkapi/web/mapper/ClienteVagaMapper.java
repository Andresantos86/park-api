package br.dev.andresantos.parkapi.web.mapper;

import br.dev.andresantos.parkapi.entity.ClienteVaga;
import br.dev.andresantos.parkapi.web.dto.estacionamento.EstacionamentoCreateDto;
import br.dev.andresantos.parkapi.web.dto.estacionamento.EstacionamentoResponseDto;
import org.modelmapper.ModelMapper;

public class ClienteVagaMapper {
  public static ClienteVaga toClientevaga(EstacionamentoCreateDto dto){
    return new ModelMapper().map(dto, ClienteVaga.class);
  }
  public static EstacionamentoResponseDto toDto(ClienteVaga clienteVaga){
    return new ModelMapper().map(clienteVaga, EstacionamentoResponseDto.class);
  }
}
