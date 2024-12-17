package hva.exceptions;

import java.io.Serial;

public class UnknownAnimalException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The animal key. */
    private final String _key;

    /**
     * @param key employee key
     */
    public UnknownAnimalException(String key) {
        _key = key;
    }

    /**
     * @return the animal key
     */
    public String getKey() { return _key; }

}
