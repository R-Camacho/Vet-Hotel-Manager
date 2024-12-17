package hva.habitat;

import hva.util.SatisfactionStrategy;

import java.io.Serial;

public class AnimalSatisfactionStrategy implements SatisfactionStrategy {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The animal. */
    private final Animal _animal;

    /**
     * @param animal
     */
    public AnimalSatisfactionStrategy(Animal animal) {
        _animal = animal;
    }

    /** @see SatisfactionStrategy#calculateSatisfaction()  */
    @Override
    public double calculateSatisfaction() {
        Habitat habitat = _animal.getHabitat();
        int same = habitat.animalsOfSpecies(_animal.getSpecies()) - 1;
        int different = habitat.numAnimals() - (same + 1);

        return 20 + 3 * same - 2 * different +
                ( (double) habitat.getArea() / habitat.numAnimals()) +
                _animal.getAdequacy();
    }


}
