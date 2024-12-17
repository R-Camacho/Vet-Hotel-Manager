package hva.exceptions;

import java.io.Serial;

public class DuplicateVaccineException extends Exception {

  @Serial
  private static final long serialVersionUID = 202407081733L;

  /** The vaccine key. */
  private final String _key;

  /**
   * @param key vaccine key
   */
  public DuplicateVaccineException(String key) {
    _key = key;
  }

  /**
   * @return the vaccine key
   */
  public String getKey() { return _key; }

}
