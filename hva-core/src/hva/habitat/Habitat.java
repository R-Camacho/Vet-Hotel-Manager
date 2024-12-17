package hva.habitat;

import hva.exceptions.UnknownAnimalException;
import hva.exceptions.UnknownTreeException;
import hva.util.Responsibility;
import hva.exceptions.InvalidAreaException;
import hva.tree.Tree;
import hva.util.Visitable;
import hva.util.Visitor;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Habitat implements Responsibility, Visitable, Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The habitat key. */
    private final String _key;

    /** The habitat name. */
    private final String _name;

    /** The habitat area. */
    private int _area;

    /** The trees planted in the habitat. */
    private final Map<String, Tree> _trees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** The animals living in the habitat. */
    private final Map<String, Animal> _animals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Maps each species in the habitat to how they are influenced */
    private final Map<Species, Influence> _influenceMap = new HashMap<>();

    /** The number of keepers who clean this habitat. */
    private int _keepers = 0;

    /**
     * @param key
     * @param name
     * @param area
     */
    public Habitat(String key, String name, int area) {
        _key = key;
        _name = name;
        _area = area;
    }

    /**
     * @return The habitat key
     */
    public String getKey() { return _key; }

    /**
     * @return the habitat name
     */
    public String getName() { return _name; }

    /**
     * @return the habitat area
     */
    public int getArea() { return _area; }

    /**
     * Sets a new area for the habitat
     * @param area new area
     * @throws InvalidAreaException if area is not positive
     */
    public void setArea(int area) throws InvalidAreaException {
        if (area <= 0)
            throw new InvalidAreaException(area);
        _area = area;
    }

    /**
     * Plants a tree in the habitat (used when importing from file)
     * @param tree tree to plant
     */
    public void plantTree(Tree tree) {
        _trees.put(tree.getKey(), tree);
    }

    /**
     * Adds an animal to the habitat
     * @param animal animal to add
     */
    public void addAnimal(Animal animal) {
        _animals.put(animal.getKey(), animal);
    }

    /**
     * Remove animal from habitat
     * @param animal animal to remove
     */
    public void removeAnimal(Animal animal) {
        _animals.remove(animal.getKey());
    }

    /**
     * Finds an animal in the habitat
     * @param key animal key
     * @return the animal
     * @throws UnknownAnimalException if animal does not exist (in this habitat)
     */
    public Animal findAnimal(String key) throws UnknownAnimalException {
        if (!_animals.containsKey(key))
            throw new UnknownAnimalException(key);
        return _animals.get(key);
    }

    /**
     * Finds a tree in the habitat
     * @param key tree key
     * @throws UnknownTreeException if tree does not exist (in this habitat)
     */
    public void findTree(String key) throws UnknownTreeException {
        if (!_trees.containsKey(key))
            throw new UnknownTreeException(key);
    }

    /**
     * @return the number of trees in the habitat
     */
    public int numTrees() {
        return _trees.size();
    }

    /**
     * Checks if habitat has trees
     * @return does the habitat have trees?
     */
    public boolean hasTrees() {
        return !_trees.isEmpty();
    }

    /**
     * Gets all trees planted in the habitat
     * @return A collection of all trees in the habitat
     */
    public Collection<Tree> getAllTrees() {
        return Collections.unmodifiableCollection(_trees.values());
    }

    /**
     * Gets all animals living in the habitat
     * @return A collection of all animals in the habitat
     */
    public Collection<Animal> getAllAnimals() {
        return Collections.unmodifiableCollection(_animals.values());
    }

    /**
     * @return the number of animals living in this habitat
     */
    public int numAnimals() { return _animals.size(); }

    /**
     * @return the number of keepers who clean this habitat
     */
    public int numKeepers() { return _keepers; }

    /**
     * Adds a new keeper to this Habitat
     */
    public void addKeeper() { _keepers++; }

    /**
     * Removes a keeper from this habitat
     */
    public void removeKeeper() { _keepers--; }

    /**
     * Calculates the habitat workload for a keeper
     * @return habitat workload
     */
    public double calculateWorkload() {
        return _area + 3 * numAnimals() + getAllTrees().stream()
                .mapToDouble(Tree::getDifficulty)
                .sum();
    }

    /**
     * @param species Species
     * @return the number of animals of the same species
     */
    public int animalsOfSpecies(Species species) {
        return (int) _animals.values().stream()
                .filter(animal -> species.equals(animal.getSpecies()))
                .count();
    }

    /**
     * Gets the influence this habitat has on this species
     * @param species species
     * @return the influence this habitat has on the species
     */
    public Influence getInfluence(Species species) {
        if (_influenceMap.get(species) == null)
            return new Influence();

        return _influenceMap.get(species);
    }

    /**
     * Change the habitat influence on a species
     * @param species species
     * @param polarity new influence polarity
     */
    public void changeSpeciesInfluence
            (Species species, InfluencePolarity polarity) {
        _influenceMap.put(species, new Influence(polarity));
    }

    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
