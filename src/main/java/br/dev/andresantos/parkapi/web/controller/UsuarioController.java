package br.dev.andresantos.parkapi.web.controller;

import br.dev.andresantos.parkapi.entity.Usuario;
import br.dev.andresantos.parkapi.service.UsuarioService;
import br.dev.andresantos.parkapi.web.dto.UsuarioDtoReq;
import br.dev.andresantos.parkapi.web.dto.UsuarioDtoRes;
import br.dev.andresantos.parkapi.web.dto.UsuarioSenhaDto;
import br.dev.andresantos.parkapi.web.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {
  private final UsuarioService usuarioService;
  @PostMapping
  public ResponseEntity<UsuarioDtoRes> create(@Valid @RequestBody UsuarioDtoReq form){
    Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(form));
    return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UsuarioDtoRes> findById(@PathVariable Long id){
    Usuario user = usuarioService.buscarPorId(id);
    return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDto(user));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDto form){
    Usuario user = usuarioService.atualizarSenha(id, form.getSenhaAtual(), form.getNovaSenha(),form.getConfirmaSenha());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
  @GetMapping
  public ResponseEntity<List<UsuarioDtoRes>> getAll(){
    List<Usuario> users = usuarioService.buscarTodos();
    return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDto(users));
  }

}
