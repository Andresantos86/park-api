package br.dev.andresantos.parkapi.service.exception;

public class CodigoUniqueViolationException extends RuntimeException {
  public CodigoUniqueViolationException(String message) {
    super(message);
  }
}
