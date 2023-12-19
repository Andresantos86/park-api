package br.dev.andresantos.parkapi.web.controller;

import br.dev.andresantos.parkapi.entity.Usuario;
import br.dev.andresantos.parkapi.service.UsuarioService;
import br.dev.andresantos.parkapi.web.dto.UsuarioDtoReq;
import br.dev.andresantos.parkapi.web.dto.UsuarioDtoRes;
import br.dev.andresantos.parkapi.web.dto.UsuarioSenhaDto;
import br.dev.andresantos.parkapi.web.exception.ErrorMessage;
import br.dev.andresantos.parkapi.web.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro" +
        "edição e leitura de um usuário")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

  @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
          responses = {
                  @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                          content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDtoRes.class))),
                  @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                  @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
          })
  @PostMapping
  public ResponseEntity<UsuarioDtoRes> create(@Valid @RequestBody UsuarioDtoReq form){
    Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(form));
    return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
  }

  @Operation(summary = "Recuperar um usuário pelo id", description = "Recuperar um usuário pelo id",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                          content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDtoRes.class))),
                  @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
          })
  @GetMapping("/{id}")
  public ResponseEntity<UsuarioDtoRes> findById(@PathVariable Long id){
    Usuario user = usuarioService.buscarPorId(id);
    return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDto(user));
  }

  @Operation(summary = "Atualizar senha", description = "Atualizar senha",
          responses = {
                  @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso",
                          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                  @ApiResponse(responseCode = "400", description = "Senha não confere",
                          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                  @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                  @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatados",
                          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
          })
  @PatchMapping("/{id}")
  public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDto form){
    Usuario user = usuarioService.atualizarSenha(id, form.getSenhaAtual(), form.getNovaSenha(),form.getConfirmaSenha());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
  @Operation(summary = "Listar todos os usuários", description = "Listar todos os usuários cadastrados",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                          content = @Content(mediaType = "application/json",
                                  array = @ArraySchema(schema = @Schema(implementation = UsuarioDtoRes.class))))
          })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<UsuarioDtoRes>> getAll(){
    List<Usuario> users = usuarioService.buscarTodos();
    return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDto(users));
  }

}
