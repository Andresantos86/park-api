package br.dev.andresantos.parkapi.service.exception;

public class CpfUniqueViolationException extends RuntimeException {
  public CpfUniqueViolationException(String message) {
    super(message);
  }
}
