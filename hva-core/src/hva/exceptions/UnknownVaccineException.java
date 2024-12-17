package hva.exceptions;

import java.io.Serial;

public class UnknownVaccineException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The vaccine key. */
    private final String _key;

    /**
     * @param key vaccine key
     */
    public UnknownVaccineException(String key) {
        _key = key;
    }

    /**
     * @return the vaccine key
     */
    public String getKey() { return _key; }

}