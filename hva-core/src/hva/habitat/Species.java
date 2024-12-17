package hva.habitat;

import hva.util.Responsibility;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Species implements Responsibility, Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The species key */
    private final String _key;

    /** The species name */
    private final String _name;

    /** The list of animals of this species */
    private final Map<String, Animal> _animals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** The number of veterinarians responsible for this species. */
    private int _veterinarians = 0;

    /**
     * @param key species key
     * @param name species name
     */
    public Species(String key, String name) {
        _key = key;
        _name = name;
    }

    /**
     * @return the species key
     */
    public String getKey() { return _key; }

    /**
     * @return the species name
     */
    public String getName() { return _name; }

    /**
     * @return a map of animals of this species (ordered)
     */
    public Map<String, Animal> getAnimals() { return _animals; }

    /**
     * @return the number of animals of this species
     */
    public int numAnimals() { return _animals.size(); }

    /**
     * @return the number of veterinarians responsible for this species
     */
    public int numVeterinarians() { return _veterinarians; }

    /**
     * Adds a new veterinarian to this species
     */
    public void addVeterinarian() { _veterinarians++; }

    /**
     * Removes a veterinarian of this habitat
     */
    public void removeVeterinarian() { _veterinarians--; }

    /**
     * Add a new animal to this species
     * @param animal animal to had
     */
    public void addAnimal(Animal animal) {
        _animals.put(animal.getKey(), animal);
    }

}
