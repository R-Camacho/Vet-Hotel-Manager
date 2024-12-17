package hva.app;

import hva.habitat.Animal;
import hva.employee.Employee;
import hva.employee.Keeper;
import hva.employee.Veterinarian;
import hva.habitat.Habitat;
import hva.tree.deciduous.*;
import hva.tree.evergreen.*;
import hva.tree.Tree;
import hva.util.Visitor;
import hva.vaccine.MedicalAct;
import hva.vaccine.Vaccine;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Renderer extends Visitor<String> {

    /** @see hva.util.Visitor#visit(Animal) */
    @Override
    public String visit(Animal animal) {
        return new StringJoiner("|")
                .add("ANIMAL")
                .add(animal.getKey())
                .add(animal.getName())
                .add(animal.getSpecies().getKey())
                .add(medicalHistory(animal.getMedicalHistory()))
                .add(animal.getHabitat().getKey())
                .toString();
    }

    /** @see hva.util.Visitor#visit(Habitat) */
    @Override
    public String visit(Habitat habitat) {
        StringJoiner joiner = new StringJoiner("|");

        joiner.add("HABITAT")
              .add(habitat.getKey())
              .add(habitat.getName())
              .add(Integer.toString(habitat.getArea()))
              .add(Integer.toString(habitat.numTrees()));

        String allTrees = "";
        if (habitat.hasTrees()) {
            allTrees = "\n" + habitat.getAllTrees()
                    .stream()
                    .map(v -> v.accept(this))
                    .collect(Collectors.joining("\n"));
        }

        return joiner + allTrees;
    }

    /** @see hva.util.Visitor#visit(Employee) */
    @Override
    public String visit(Employee employee) {
        return new StringJoiner("|")
                .add(employee.getKey())
                .add(employee.getName())
                .toString();
    }

    /** @see hva.util.Visitor#visit(Keeper) */
    @Override
    public String visit(Keeper keeper) {
        return "TRT|" + this.visit((Employee) keeper) +
                (keeper.hasResponsibilities() ?
                "|" + String.join(",",
                        keeper.getResponsibilityKeys())
                : "");
    }

    /** @see hva.util.Visitor#visit(Veterinarian) */
    @Override
    public String visit(Veterinarian veterinarian) {
        return "VET|" + this.visit((Employee) veterinarian) +
                (veterinarian.hasResponsibilities() ?
                "|" + String.join(",",
                        veterinarian.getResponsibilityKeys())
                : "");
    }

    /** @see hva.util.Visitor#visit(Vaccine) */
    @Override
    public String visit(Vaccine vaccine) {
        return new StringJoiner("|")
                .add("VACINA")
                .add(vaccine.getKey())
                .add(vaccine.getName())
                .add(Integer.toString(vaccine.timesApplied()))
                + (vaccine.hasSpecies() ?
                "|" + String.join(",",
                        vaccine.getSpeciesKeys()) : "");
    }

    /** @see hva.util.Visitor#visit(MedicalAct) */
    @Override
    public String visit(MedicalAct medicalAct) {
        return new StringJoiner("|")
                .add("REGISTO-VACINA")
                .add(medicalAct.getVaccine().getKey())
                .add(medicalAct.getVeterinarian().getKey())
                .add(medicalAct.getAnimal().getSpecies().getKey())
                .toString();

    }

    private String medicalHistory(List<MedicalAct> history) {
        if (history.isEmpty())
            return "VOID";

        StringJoiner joiner = new StringJoiner(",");

        for (MedicalAct act : history) {
            int damage = act.calculateDamage();

            if (damage == 0) {
                joiner.add(act.isWrongVaccination() ? "CONFUSÃO" : "NORMAL");
            }
            else if (1 <= damage && damage <= 4)
                joiner.add("ACIDENTE");
            else
                joiner.add("ERRO");
        }

        return joiner.toString();
    }




    /** @see hva.util.Visitor#visit(Tree)  */
    @Override
    public String visit(Tree tree) {
        return new StringJoiner("|")
                .add("ÁRVORE")
                .add(tree.getKey())
                .add(tree.getName())
                .add(Integer.toString(tree.getAge()))
                .add(Integer.toString(tree.getBaseDifficulty()))
                .toString();
    }

    /** @see hva.util.Visitor#visit(DeciduousTree)  */
    @Override
    public String visit(DeciduousTree deciduousTree) {
        return this.visit((Tree) deciduousTree)
                + "|CADUCA|"
                + deciduousTree.getSeasonalCycle().accept(this);
    }

    /** @see hva.util.Visitor#visit(DeciduousSpring) */
    @Override
    public String visit(DeciduousSpring deciduousSpring) {
        return "GERARFOLHAS";
    }

    /** @see hva.util.Visitor#visit(DeciduousSummer) */
    @Override
    public String visit(DeciduousSummer deciduousSummer) {
        return "COMFOLHAS";
    }

    /** @see hva.util.Visitor#visit(DeciduousFall) */
    @Override
    public String visit(DeciduousFall deciduousFall) {
        return "LARGARFOLHAS";
    }

    /** @see hva.util.Visitor#visit(DeciduousWinter) */
    @Override
    public String visit(DeciduousWinter deciduousWinter) {
        return "SEMFOLHAS";
    }

    /** @see hva.util.Visitor#visit(EvergreenTree) */
    @Override
    public String visit(EvergreenTree evergreenTree) {
        return this.visit((Tree) evergreenTree)
                + "|PERENE|"
                + evergreenTree.getSeasonalCycle().accept(this);
    }

    /** @see hva.util.Visitor#visit(EvergreenSpring) */
    @Override
    public String visit(EvergreenSpring evergreenSpring) {
        return "GERARFOLHAS";
    }

    /** @see hva.util.Visitor#visit(EvergreenSummer) */
    @Override
    public String visit(EvergreenSummer evergreenSummer) {
        return "COMFOLHAS";
    }

    /** @see hva.util.Visitor#visit(EvergreenFall) */
    @Override
    public String visit(EvergreenFall evergreenFall) {
        return "COMFOLHAS";
    }

    /** @see hva.util.Visitor#visit(EvergreenWinter) */
    @Override
    public String visit(EvergreenWinter evergreenWinter) {
        return "LARGARFOLHAS";
    }

}
