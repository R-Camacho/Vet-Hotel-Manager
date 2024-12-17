package hva.exceptions;

import java.io.Serial;

public class UnknownSpeciesException extends Exception {

  @Serial
  private static final long serialVersionUID = 202407081733L;

  /** The species key. */
  private final String _key;

  /**
   * @param key species key
   */
  public UnknownSpeciesException(String key) {
    _key = key;
  }

  /**
   * @return the species key
   */
  public String getKey() { return _key; }

}