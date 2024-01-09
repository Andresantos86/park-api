package br.dev.andresantos.parkapi.web.mapper;

import br.dev.andresantos.parkapi.web.dto.PageableDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class PageableMapper {

  public static PageableDto toDto(Page page){
    return new ModelMapper().map(page, PageableDto.class);
  }
}
