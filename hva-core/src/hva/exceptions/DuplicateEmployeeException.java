package hva.exceptions;

import java.io.Serial;

public class DuplicateEmployeeException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The employee key. */
    private final String _key;

    /**
     * @param key employee key
     */
    public DuplicateEmployeeException(String key) {
        _key = key;
    }

    /**
     * @return the employee key
     */
    public String getKey() { return _key; }

}
