package hva.exceptions;

import java.io.Serial;

public class ResponsibilityException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The responsibility key. */
    private final String _key;

    /**
     * @param key responsibility key
     */
    public ResponsibilityException(String key) { _key = key; }

    /**
     * @return the responsibility key
     */
    public String getKey() { return _key; }
}
