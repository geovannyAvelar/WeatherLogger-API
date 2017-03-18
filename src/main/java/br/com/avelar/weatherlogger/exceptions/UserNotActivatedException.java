package br.com.avelar.weatherlogger.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

  private static final long serialVersionUID = 3975311858670238633L;

  public UserNotActivatedException(String msg, Throwable t) {
    super(msg, t);
  }

  public UserNotActivatedException(String msg) {
    super(msg);
  }
}
