package br.dev.andresantos.parkapi.web.mapper;

import br.dev.andresantos.parkapi.entity.Usuario;
import br.dev.andresantos.parkapi.web.dto.UsuarioDtoReq;
import br.dev.andresantos.parkapi.web.dto.UsuarioDtoRes;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

  public static Usuario toUsuario (UsuarioDtoReq form) {
    return new ModelMapper().map(form, Usuario.class);
  }

  public static UsuarioDtoRes toDto(Usuario usuario){
    String role = usuario.getRole().name().substring("ROLE_".length());
    PropertyMap<Usuario, UsuarioDtoRes> props = new PropertyMap<Usuario, UsuarioDtoRes>() {
      @Override
      protected void configure() {
        map().setRole(role);
      }
    };
    ModelMapper mapper = new ModelMapper();
    mapper.addMappings(props);
    return  mapper.map(usuario ,UsuarioDtoRes.class);
  }

  public static List<UsuarioDtoRes> toListDto(List<Usuario> usuarios){
    return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
  }
}
