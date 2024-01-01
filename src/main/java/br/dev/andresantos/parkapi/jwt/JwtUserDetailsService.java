package br.dev.andresantos.parkapi.jwt;

import br.dev.andresantos.parkapi.entity.Usuario;
import br.dev.andresantos.parkapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

  private final UsuarioService usuarioService;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioService.buscarPorUserName(username);
    return  new JwtUserDetails(usuario);
  }
  public JwtToken getTokenAuthenticated(String username){
    Usuario.Role role = usuarioService.buscarRolePorUsername(username);
    return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));

  }
}
