package hva.employee;

import hva.util.SatisfactionStrategy;
import hva.util.Responsibility;
import hva.exceptions.ResponsibilityException;
import hva.util.Visitable;
import hva.util.Visitor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

public abstract class Employee implements Serializable, Visitable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The employee key. */
    private final String _key;

    /** The employee name. */
    private final String _name;

    /** Strategy to calculate the employee satisfaction. */
    private SatisfactionStrategy _satisfactionStrategy;

    /**
     * @param key
     * @param name
     */
    public Employee(String key, String name) {
        _key = key;
        _name = name;
    }

    /**
     * @return the employee key
     */
    public final String getKey() { return _key; }

    /**
     * @return the employee name
     */
    public final String getName() { return _name; }

    /**
     * Adds a new responsibility to the employee
     * @param responsibility the responsibility to add
     */
    public abstract void addResponsibility(Responsibility responsibility)
            throws ResponsibilityException;

    /**
     * Removes the responsibility from the employee
     * @param responsibility responsibility to remove
     * @throws ResponsibilityException if employee is not associated
     * with responsibility
     */
    public abstract void removeResponsibility(Responsibility responsibility)
            throws ResponsibilityException;

    /**
     * Checks if employees has any responsibility
     * @return does employee hava any responsibility?
     */
    public abstract boolean hasResponsibilities();

    /**
     * Returns a collection of the keys of all responsibilities
     * associated with this employee
     * @return All the responsibility keys, in natural lexicographic order
     */
    public abstract Collection<String> getResponsibilityKeys();

    /**
     * @return the satisfaction of this employee
     */
    public double calculateSatisfaction() {
        return _satisfactionStrategy.calculateSatisfaction();
    }

    /**
     * @return the employee satisfaction, rounded to the nearest integer
     */
    public int calculateSatisfactionRounded() {
        return (int) Math.round(_satisfactionStrategy.calculateSatisfaction());
    }

    /**
     * Sets the strategy to use to calculate the satisfaction
     * @param satisfactionStrategy
     */
    protected void setSatisfactionStrategy
            (SatisfactionStrategy satisfactionStrategy) {
        _satisfactionStrategy = satisfactionStrategy;
    }

    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
