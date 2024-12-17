package hva.employee;

import hva.util.Responsibility;
import hva.habitat.Species;
import hva.exceptions.ResponsibilityException;
import hva.util.Visitor;
import hva.vaccine.MedicalAct;

import java.io.Serial;
import java.util.*;

public class Veterinarian extends Employee {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** Species that this veterinarian can vaccinate. */
    private final Map<String, Species> _species = new  TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** The medical acts done by this veterinarian. */
    private final List<MedicalAct> _medicalActs = new ArrayList<>();

    /**
     * @param key veterinarian key
     * @param name veterinarian name
     */
    public Veterinarian(String key, String name ) {
        super(key, name);
        super.setSatisfactionStrategy(new VeterinarianSatisfactionStrategy(this));
    }

    /**
     * Gets all the species this vet is responsible for
     * @return A collection of the species this vet treats
     */
    public Collection<Species> getSpecies() {
        return Collections.unmodifiableCollection(_species.values());
    }

    /**
     * Gets all the vaccination events by this veterinarian,
     * sorted by application order
     * @return A list of vaccinations done by this veterinarian
     */
    public List<MedicalAct> getMedicalActs() {
        return Collections.unmodifiableList(_medicalActs);
    }

    /**
     * Add medical
     * @param medicalAct medical act to add
     */
    public void addMedicalAct(MedicalAct medicalAct) {
        _medicalActs.add(medicalAct);
    }

    /**
     * Adds a new responsibility to the employee
     * @param responsibility the responsibility to add
     */
    @Override
    public void addResponsibility(Responsibility responsibility)
        throws ResponsibilityException {
        try {
            Species species = (Species) responsibility;
            species.addVeterinarian();
            _species.put(species.getKey(), species);
        } catch (ClassCastException e) {
            throw new ResponsibilityException(responsibility.getKey());
        }
    }

    /**
     * Removes the responsibility from the employee
     * @param responsibility responsibility to remove
     * @throws ResponsibilityException if employee is not associated
     * with responsibility
     * @see Employee#removeResponsibility(Responsibility) 
     */
    @Override
    public void removeResponsibility(Responsibility responsibility)
            throws ResponsibilityException {
        if (!_species.containsKey(responsibility.getKey()))
            throw new ResponsibilityException(responsibility.getKey());
        _species.remove(responsibility.getKey());
        Species species = (Species) responsibility;
        species.removeVeterinarian();
    }

    /**
     * Checks if employees has any responsibility
     * @return does employee hava any responsibility?
     * @see Employee#hasResponsibilities()
     */
    @Override
    public boolean hasResponsibilities() {
        return !_species.isEmpty();
    }

    /**
     * Returns a collection of the keys of all responsibilities
     * associated with this employee
     * @return All their responsibility keys, in natural lexicographic order
     * @see Employee#getResponsibilityKeys()
     */
    @Override
    public Collection<String> getResponsibilityKeys() {
        return _species.keySet();
    }

    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
