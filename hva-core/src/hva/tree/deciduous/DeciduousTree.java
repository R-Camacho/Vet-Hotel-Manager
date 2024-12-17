package hva.tree.deciduous;

import hva.util.Season;
import hva.tree.Tree;
import hva.util.Visitor;

import java.io.Serial;

public class DeciduousTree extends Tree {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /**
     * @param key
     * @param name
     * @param age
     * @param baseDifficulty
     */
    public DeciduousTree
    (String key, String name, int age, int baseDifficulty, Season season) {
        super(key, name, age, baseDifficulty, season);

        switch (season) {
            case SPRING -> setSeasonalCycle(new DeciduousSpring(this));
            case SUMMER -> setSeasonalCycle(new DeciduousSummer(this));
            case FALL   -> setSeasonalCycle(new DeciduousFall(this));
            case WINTER -> setSeasonalCycle(new DeciduousWinter(this));
        }
    }

    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
