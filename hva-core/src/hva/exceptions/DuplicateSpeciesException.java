package hva.exceptions;

import java.io.Serial;

public class DuplicateSpeciesException extends Exception {

  @Serial
  private static final long serialVersionUID = 202407081733L;

  /** The species key. */
  private final String _key;

  /**
   * @param key species key
   */
  public DuplicateSpeciesException(String key) {
    _key = key;
  }

  /**
   * @return the species key
   */
  public String getKey() { return _key; }

}