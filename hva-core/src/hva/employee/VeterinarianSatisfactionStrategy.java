package hva.employee;

import java.io.Serial;

public class VeterinarianSatisfactionStrategy extends EmployeeSatisfactionStrategy {

    @Serial
    private static final long serialVersionUID = 202407081733L;


    public VeterinarianSatisfactionStrategy(Veterinarian veterinarian) {
        super(veterinarian);
    }

    /** @see EmployeeSatisfactionStrategy#calculateSatisfaction()  */
    @Override
    public double calculateSatisfaction() {
        double work = ( (Veterinarian) getEmployee()).getSpecies().stream()
                .mapToDouble(species -> (double) species.numAnimals()
                        / species.numVeterinarians())
                .sum();

        return 20 - work;
    }

}
