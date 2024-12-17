package hva.employee;

import hva.util.Responsibility;
import hva.exceptions.ResponsibilityException;
import hva.habitat.Habitat;
import hva.util.Visitor;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Keeper extends Employee {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** Habitats that this keeper is responsible for. */
    private final Map<String, Habitat> _habitats = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * @param key keeper key
     * @param name keeper name
     */
    public Keeper(String key, String name) {
        super(key, name);
        super.setSatisfactionStrategy(new KeeperSatisfactionStrategy(this));
    }

    /**
     * Gets all the habitats this vet is responsible for
     * @return A collection of the habitat this keeper cleans
     */
    public Collection<Habitat> getHabitats() {
        return Collections.unmodifiableCollection(_habitats.values());
    }

    /**
     * Adds a new responsibility to the employee
     *
     * @param responsibility the responsibility to add
     * @see hva.employee.Employee#addResponsibility(Responsibility) 
     */
    @Override
    public void addResponsibility(Responsibility responsibility) throws ResponsibilityException {
        try{
            Habitat habitat = (Habitat) responsibility;
            habitat.addKeeper();
            _habitats.put(habitat.getKey(), habitat);
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
    public void removeResponsibility(Responsibility responsibility) throws ResponsibilityException {
        if (!_habitats.containsKey(responsibility.getKey()))
            throw new ResponsibilityException(responsibility.getKey());
        _habitats.remove(responsibility.getKey());
        Habitat habitat = (Habitat) responsibility;
        habitat.removeKeeper();
    }

    /**
     * Checks if employees has any responsibility
     * @return does employee hava any responsibility?
     * @see Employee#hasResponsibilities()
     */
    @Override
    public boolean hasResponsibilities() {
        return !_habitats.isEmpty();
    }

    /**
     * Returns a collection of the keys of all responsibilities
     * associated with this employee
     * @return All their responsibility keys, in natural lexicographic order
     * @see Employee#getResponsibilityKeys()
     */
    @Override
    public Collection<String> getResponsibilityKeys() {
        return _habitats.keySet();
    }

    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
