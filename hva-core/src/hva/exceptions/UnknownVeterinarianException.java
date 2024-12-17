package hva.exceptions;

import java.io.Serial;

public class UnknownVeterinarianException extends UnknownEmployeeException {

  @Serial
  private static final long serialVersionUID = 202407081733L;

  /**
   * @param key veterinarian key
   */
  public UnknownVeterinarianException(String key) {
    super(key);
  }

}
