package br.dev.andresantos.parkapi.web.mapper;

import br.dev.andresantos.parkapi.entity.Cliente;
import br.dev.andresantos.parkapi.web.dto.cliente.ClienteCreateDto;
import br.dev.andresantos.parkapi.web.dto.cliente.ClienteResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

  public static Cliente toCliente(ClienteCreateDto dto){
    return new ModelMapper().map(dto, Cliente.class);
  }

  public static ClienteResponseDto toDto(Cliente cliente){
    return new ModelMapper().map(cliente, ClienteResponseDto.class);
  }
}
