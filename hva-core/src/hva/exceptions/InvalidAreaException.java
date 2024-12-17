package hva.exceptions;

import java.io.Serial;

public class InvalidAreaException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The invalid area number. */
    private final int _area;

    /**
     * @param area invalid area number
     */
    public InvalidAreaException(int area) {
        _area = area;
    }

    /**
     * @return the invalid area number
     */
    public int getKey() { return _area; }

}