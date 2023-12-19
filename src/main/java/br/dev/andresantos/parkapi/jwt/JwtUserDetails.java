package br.dev.andresantos.parkapi.jwt;

import br.dev.andresantos.parkapi.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class JwtUserDetails extends User {
  private Usuario usuario;
  //String username, String password, Collection<? extends GrantedAuthority> authorities vamos pegar do usuario
  public JwtUserDetails(Usuario usuario) {
    super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
    this.usuario = usuario;
  }

  public Long getId(){
    return  this.usuario.getId();
  }
  public String getRole(){
    return  this.usuario.getRole().name();
  }
}
