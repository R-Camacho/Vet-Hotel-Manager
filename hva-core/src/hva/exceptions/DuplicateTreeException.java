package hva.exceptions;

import java.io.Serial;

public class DuplicateTreeException extends Exception {

  @Serial
  private static final long serialVersionUID = 202407081733L;

  /** The tree key. */
  private final String _key;

  /**
   * @param key tree key
   */
  public DuplicateTreeException(String key) {
    _key = key;
  }

  /**
   * @return the tree key
   */
  public String getKey() { return _key; }

}