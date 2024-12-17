package hva.vaccine;

import hva.habitat.Animal;
import hva.employee.Veterinarian;
import hva.util.Visitable;
import hva.util.Visitor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class MedicalAct implements Serializable, Visitable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The vaccine that was applied. */
    private final Vaccine _vaccine;

    /** The animal the vaccine was applied to. */
    private final Animal _animal;

    /** The veterinarian who applied the vaccine. */
    private final Veterinarian _veterinarian;

    /**
     * @param vaccine
     * @param animal
     * @param vet
     */
    public MedicalAct(Vaccine vaccine, Animal animal, Veterinarian vet) {
        _vaccine = vaccine;
        _animal = animal;
        _veterinarian = vet;
    }

    /**
     * @return the vaccine that was applied
     */
    public Vaccine getVaccine() { return _vaccine; }

    /**
     * @return the animal the vaccine was applied to
     */
    public Animal getAnimal() { return _animal; }

    /**
     * @return the veterinarian who applied the vaccine
     */
    public Veterinarian getVeterinarian() { return _veterinarian; }

    /**
     * Checks if the animals species was safe for this vaccination
     * @return if animal species is safe
     */
    private boolean sameSpecies() {
        return _vaccine.isSpeciesValid(_animal.getSpecies());
    }

    /**
     * Checks if the animals species was not safe for this vaccination
     * @return true if animal was not safe
     */
    public boolean isWrongVaccination() {
        return !sameSpecies();
    }

    /**
     * @return the damage the vaccine caused in the animal
     */
    public int calculateDamage() {
        if (sameSpecies())
            return 0;

        int max = 0;
        String animalSpecies = _animal.getSpecies().getName();
        for (String speciesName : _vaccine.getSpeciesNames()) {
            int damage = Math.max(speciesName.length(), animalSpecies.length())
                    - commonCharacters(speciesName, animalSpecies);
            max = Math.max(max, damage);
        }
        return max;
    }


    /**
     * Counts the common characters in 2 strings, ignoring casing,
     * i.e.
     * @param str1
     * @param str2
     * @return the number of common characters
     */
    private int commonCharacters(String str1, String str2) {
        Set<Character> set1 = new HashSet<>();
        Set<Character> set2 = new HashSet<>();

        for (char c : str1.toLowerCase().toCharArray())
            set1.add(c);

        for (char c : str2.toLowerCase().toCharArray())
            set2.add(c);

        set1.retainAll(set2);
        return set1.size();
    }


    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
