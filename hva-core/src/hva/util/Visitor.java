package hva.util;

import hva.habitat.Animal;
import hva.employee.Employee;
import hva.employee.Keeper;
import hva.employee.Veterinarian;
import hva.habitat.Habitat;
import hva.tree.*;
import hva.tree.deciduous.*;
import hva.tree.evergreen.*;
import hva.vaccine.MedicalAct;
import hva.vaccine.Vaccine;

public abstract class Visitor<T> {

    public abstract T visit(Animal animal);

    public abstract T visit(Habitat habitat);

    public abstract T visit(Employee employee);

    public abstract T visit(Keeper keeper);

    public abstract T visit(Veterinarian veterinarian);

    public abstract T visit(Vaccine vaccine);

    public abstract T visit(MedicalAct medicalAct);

    public abstract T visit(Tree tree);

    public abstract T visit(DeciduousTree deciduousTree);

    public abstract T visit(DeciduousSpring deciduousSpring);

    public abstract T visit(DeciduousSummer deciduousSummer);

    public abstract T visit(DeciduousFall deciduousFall);

    public abstract T visit(DeciduousWinter deciduousWinter);

    public abstract T visit(EvergreenTree evergreenTree);

    public abstract T visit(EvergreenSpring evergreenSpring);

    public abstract T visit(EvergreenSummer evergreenSummer);

    public abstract T visit(EvergreenFall evergreenFall);

    public abstract T visit(EvergreenWinter evergreenWinter);

}
