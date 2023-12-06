package br.dev.andresantos.parkapi.service.exception;

public class PasswordInvalidException extends RuntimeException {
  public PasswordInvalidException(String message) {
    super(message);
  }
}
