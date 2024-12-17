package hva.exceptions;

import java.io.Serial;

public class InvalidEntryException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** Invalid entry specification. */
    private final String _entrySpecification;

    public InvalidEntryException(String entrySpecification) {
        _entrySpecification = entrySpecification;
    }

    public InvalidEntryException(String entrySpecification, Exception cause) {
        super(cause);
        _entrySpecification = entrySpecification;
    }

    public String getEntrySpecification() {
        return _entrySpecification;
    }

}