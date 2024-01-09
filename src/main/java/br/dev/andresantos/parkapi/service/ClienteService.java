package br.dev.andresantos.parkapi.service;

import br.dev.andresantos.parkapi.entity.Cliente;
import br.dev.andresantos.parkapi.repository.ClienteRepository;
import br.dev.andresantos.parkapi.repository.projection.ClienteProjection;
import br.dev.andresantos.parkapi.service.exception.CpfUniqueViolationException;
import br.dev.andresantos.parkapi.service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
  private final ClienteRepository repository;

  @Transactional
  public Cliente salvar(Cliente cliente){
    try {
      return repository.save(cliente);
    }catch (DataIntegrityViolationException e){
      throw new CpfUniqueViolationException(String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", cliente.getCpf()));
    }
  }

  @Transactional(readOnly = true)
  public Cliente BuscarPorId(Long id) {
    return repository.findById(id).orElseThrow(
            ()-> new EntityNotFoundException(String.format("Cliente id=%s não encontrado no sistema",id))
    );
  }

  @Transactional(readOnly = true)
  public Page<ClienteProjection> BuscarTodos(Pageable page) {
    return repository.findAllPegeable(page);
  }

  @Transactional(readOnly = true)
  public Cliente buscarPorUsuarioId(Long id) {
    return repository.findByUsuarioId(id);
  }
}
