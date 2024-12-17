package hva.tree.evergreen;

import hva.util.Season;
import hva.tree.Tree;
import hva.util.Visitor;

import java.io.Serial;

public class EvergreenTree extends Tree {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /**
     * @param key
     * @param name
     * @param age
     * @param baseDifficulty
     */
    public EvergreenTree
    (String key, String name, int age, int baseDifficulty, Season season) {
        super(key, name, age, baseDifficulty, season);

        switch (season) {
            case SPRING -> setSeasonalCycle(new EvergreenSpring(this));
            case SUMMER -> setSeasonalCycle(new EvergreenSummer(this));
            case FALL   -> setSeasonalCycle(new EvergreenFall(this));
            case WINTER -> setSeasonalCycle(new EvergreenWinter(this));
        }
    }

    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
