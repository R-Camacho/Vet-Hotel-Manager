package hva.exceptions;

import java.io.Serial;

public class VeterinarianAuthorisationException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The veterinarian key. */
    private final String _veterinarianKey;

    /** The Species key. */
    private final String _speciesKey;

    public VeterinarianAuthorisationException
            (String veterinarianKey, String speciesKey) {
        _veterinarianKey = veterinarianKey;
        _speciesKey = speciesKey;
    }

    /**
     * @return the veterinarian key
     */
    public String getVeterinarianKey() { return _veterinarianKey; }

    /**
     * @return the Species key
     */
    public String getSpeciesKey() { return _speciesKey; }

}
