package hva.employee;

import java.io.Serial;

public class KeeperSatisfactionStrategy extends EmployeeSatisfactionStrategy {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    public KeeperSatisfactionStrategy(Keeper keeper) {
        super(keeper);
    }

    /** @see EmployeeSatisfactionStrategy#calculateSatisfaction()  */
    @Override
    public double calculateSatisfaction() {

        double work = ( (Keeper) getEmployee()).getHabitats().stream()
                .mapToDouble(habitat -> habitat.calculateWorkload() / habitat.numKeepers())
                .sum();

        return 300 - work;
    }

}
