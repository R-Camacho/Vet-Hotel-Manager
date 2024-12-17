package hva.employee;

import hva.util.SatisfactionStrategy;

import java.io.Serial;

public abstract class EmployeeSatisfactionStrategy implements SatisfactionStrategy {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    private final Employee _employee;

    public EmployeeSatisfactionStrategy(Employee employee) {
        _employee = employee;
    }

    /**
     * @return the employee
     */
    public Employee getEmployee() { return _employee; }

    /** @see SatisfactionStrategy#calculateSatisfaction() */
    public abstract double calculateSatisfaction();

}
