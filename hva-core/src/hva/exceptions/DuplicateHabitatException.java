package hva.exceptions;

import java.io.Serial;

public class DuplicateHabitatException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The habitat key. */
    private final String _key;

    /**
     * @param key habitat key
     */
    public DuplicateHabitatException(String key) {
        _key = key;
    }

    /**
     * @return the habitat key
     */
    public String getKey() { return _key; }

}
