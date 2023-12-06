package br.dev.andresantos.parkapi.service;

import br.dev.andresantos.parkapi.entity.Usuario;
import br.dev.andresantos.parkapi.repository.UsuarioRepository;
import br.dev.andresantos.parkapi.exception.UsernameUniqueViolationException;
import br.dev.andresantos.parkapi.service.exception.EntityNotFoundException;
import br.dev.andresantos.parkapi.service.exception.PasswordInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;

  @Transactional
  public Usuario salvar(Usuario usuario) {
    try {
      return usuarioRepository.save(usuario);
    }catch (DataIntegrityViolationException ex){
      throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado", usuario.getUsername()));
    }
  }
  @Transactional(readOnly = true)
  public Usuario buscarPorId(Long id) {
    return usuarioRepository.findById(id).orElseThrow(
            ()-> new EntityNotFoundException("Usuário não encontrado."));
  }

  @Transactional
  public Usuario atualizarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
    if(!novaSenha.equals(confirmaSenha)){
      throw new PasswordInvalidException("Nova senha não confere com a confirmação de senha");
    }
    Usuario usuario = buscarPorId(id);
    if(!usuario.getPassword().equals(senhaAtual)){
      throw new PasswordInvalidException("Sua senha não confere");
    }
    usuario.setPassword(novaSenha);
    return usuario;
  }
  @Transactional(readOnly = true)
  public List<Usuario> buscarTodos() {
    List<Usuario> usuarios = usuarioRepository.findAll();
    return usuarios;
  }
}
