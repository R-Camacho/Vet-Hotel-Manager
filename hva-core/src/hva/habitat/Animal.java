package hva.habitat;

import hva.util.SatisfactionStrategy;
import hva.util.Visitable;
import hva.util.Visitor;
import hva.vaccine.MedicalAct;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Animal implements Serializable, Visitable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The animal key. */
    private final String _key;

    /** The animal name. */
    private final String _name;

    /** The animal species. */
    private final Species _species;

    /** The habitat the animal is currently in. */
    private Habitat _habitat;

    /** The medical acts on this animal. */
    private final List<MedicalAct> _medicalActs = new ArrayList<>();

    private final SatisfactionStrategy _satisfactionStrategy =
            new AnimalSatisfactionStrategy(this);

    /**
     * @param key animal key
     * @param name animal name
     * @param species animal species
     * @param habitat animal habitat
     */
    public Animal(String key, String name, Species species, Habitat habitat) {
        _key = key;
        _name = name;
        _species = species;
        _habitat = habitat;
    }

    /**
     * @return the animal key
     */
    public String getKey() { return _key; }

    /**
     * @return the animal name
     */
    public String getName() { return _name; }

    /**
     * @return the animal species
     */
    public Species getSpecies() { return _species; }

    /**
     * @return the animal habitat
     */
    public Habitat getHabitat() { return _habitat; }

    /**
     * @return the animal satisfaction
     */
    public double calculateSatisfaction() {
        return _satisfactionStrategy.calculateSatisfaction();
    }

    /**
     * @return the animal satisfaction, rounded to the nearest integer
     */
    public int calculateSatisfactionRounded() {
        return (int) Math.round(_satisfactionStrategy.calculateSatisfaction());
    }

    public List<MedicalAct> getMedicalHistory() {
        return Collections.unmodifiableList(_medicalActs);
    }

    /**
     * Gets the adequacy to the animal habitat
     * @return Adequacy: 20 if positive, 0 if neutral, -20 if negative
     */
    public int getAdequacy() {
        return _habitat.getInfluence(_species).getPolarity();
    }

    /**
     * Handles transfer of this animal to new habitat
     * @param habitat new habitat
     */
    public void transferTo(Habitat habitat) {
        _habitat.removeAnimal(this);
        _habitat = habitat;
        _habitat.addAnimal(this);
    }

    /**
     * Vaccinates animal
     * @param medicalAct Medical act
     */
    public void vaccinate(MedicalAct medicalAct) {
        _medicalActs.add(medicalAct);
    }
    
    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
