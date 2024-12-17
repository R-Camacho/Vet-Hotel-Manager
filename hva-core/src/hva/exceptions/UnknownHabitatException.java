package hva.exceptions;

import java.io.Serial;

public class UnknownHabitatException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The habitat key. */
    private final String _key;

    /**
     * @param key habitat key
     */
    public UnknownHabitatException(String key) {
        _key = key;
    }

    /**
     * @return the habitat key
     */
    public String getKey() { return _key; }

}