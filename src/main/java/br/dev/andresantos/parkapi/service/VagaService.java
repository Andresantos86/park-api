package br.dev.andresantos.parkapi.service;


import br.dev.andresantos.parkapi.entity.Vaga;
import br.dev.andresantos.parkapi.repository.VagaRepository;
import br.dev.andresantos.parkapi.service.exception.CodigoUniqueViolationException;
import br.dev.andresantos.parkapi.service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.dev.andresantos.parkapi.entity.Vaga.StatusVaga.LIVRE;

@Service
@RequiredArgsConstructor
public class VagaService {
  private final VagaRepository repository;

  @Transactional
  public Vaga salvar (Vaga vaga){
    try {
      return repository.save(vaga);
    }catch (DataIntegrityViolationException e){
      throw new CodigoUniqueViolationException(String.format("Vaga com código '%s' já cadastrada",vaga.getCodigo()));
    }
  }

  @Transactional(readOnly = true)
  public Vaga buscarPorCodigo(String codigo){
    return repository.findByCodigo(codigo).orElseThrow(
            ()-> new EntityNotFoundException(String.format("Vaga com código '%s' não foi encontrada", codigo))
    );
  }

  @Transactional(readOnly = true)
  public Vaga buscarPorVagaLivre() {
    return repository.findFirstByStatus(LIVRE).orElseThrow(
            ()-> new EntityNotFoundException("Nenhuma vaga livre foi encontrada"));
  }
}


