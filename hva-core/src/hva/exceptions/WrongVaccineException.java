package hva.exceptions;

import java.io.Serial;

public class WrongVaccineException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    public WrongVaccineException() {
        // empty: default
    }

}
