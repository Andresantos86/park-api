package br.dev.andresantos.parkapi.service;

import br.dev.andresantos.parkapi.entity.Cliente;
import br.dev.andresantos.parkapi.entity.ClienteVaga;
import br.dev.andresantos.parkapi.entity.Vaga;
import br.dev.andresantos.parkapi.util.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {
  private final ClienteVagaService clienteVagaService;
  private final ClienteService clienteService;
  private final VagaService vagaService;

  @Transactional
  public ClienteVaga checkIn (ClienteVaga clienteVaga){
    Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());
    clienteVaga.setCliente(cliente);

    Vaga vaga = vagaService.buscarPorVagaLivre();
    vaga.setStatus(Vaga.StatusVaga.OCUPADA);
    clienteVaga.setVaga(vaga);

    clienteVaga.setDataEntrada(LocalDateTime.now());
    clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

    return clienteVagaService.salvar(clienteVaga);
  }

  @Transactional
  public ClienteVaga checkout(String recibo) {
    ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
    LocalDateTime dataSaida = LocalDateTime.now();
    BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(),dataSaida);
    clienteVaga.setValor(valor);

    long totalVezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());
    BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totalVezes);
    clienteVaga.setDesconto(desconto);
    clienteVaga.setDataSaida(dataSaida);
    clienteVaga.getVaga().setStatus(Vaga.StatusVaga.LIVRE);
    return clienteVagaService.salvar(clienteVaga);
  }
}
