package hva.vaccine;

import hva.habitat.Species;
import hva.util.Visitable;
import hva.util.Visitor;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Vaccine implements Serializable, Visitable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The vaccine key. */
    private final String _key;

    /** The vaccine name. */
    private final String _name;

    /** The species this vaccine can be applied to. */
    private final Map<String, Species> _species = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** How many times has this vaccine been applied. */
    private int _timesApplied = 0;

    /**
     * @param key vaccine key
     * @param name vaccine name
     */
    public Vaccine(String key, String name) {
        _key = key;
        _name = name;
    }

    /**
     * @return the vaccine key
     */
    public String getKey() { return _key; }

    /**
     * @return the vaccine name
     */
    public String getName() { return _name; }

    /**
     * Adds a new species to the collection of valid ones in this vaccine
     * @param species species to add
     */
    public void addSpecies(Species species) {
        _species.put(species.getKey(), species);
    }

    /**
     * Returns how many medical acts are associated with this vaccine
     * @return the amount of times this vaccine has been applied
     */
    public int timesApplied() {
        return _timesApplied;
    }

    /**
     * Adds 1 to the counter of applications
     */
    public void apply() {
        _timesApplied++;
    }

    /**
     * Checks if this vaccine is appropriate to any species
     * @return Can any species take this vaccine without harm?
     */
    public boolean hasSpecies() {
        return !_species.isEmpty();
    }

    public boolean isSpeciesValid(Species species) {
        return _species.containsKey(species.getKey());
    }

    /**
     * Returns a collection of the keys of the species that can be
     * vaccinated by this vaccine
     * @return All the species keys, in natural lexicographic order
     */
    public Collection<String> getSpeciesKeys() {
        return _species.keySet();
    }

    /**
     * Returns a collection of the names of the species that can be
     * vaccinated by this vaccine
     * @return ALl the species names
     */
    public Collection<String> getSpeciesNames() {
        return _species.values().stream()
                .map(Species::getName)
                .toList();
    }

    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}


