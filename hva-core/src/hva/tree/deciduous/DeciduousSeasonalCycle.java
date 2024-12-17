package hva.tree.deciduous;

import hva.util.Season;
import hva.tree.SeasonalCycle;
import hva.tree.Tree;

import java.io.Serial;

public abstract class DeciduousSeasonalCycle extends SeasonalCycle {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    public DeciduousSeasonalCycle(Tree tree, Season season) {
        super(tree);
        setSeason(season);
    }

    /**
     * @return the seasonal effort to clean a tree
     * (Depends on the tree type and current season)
     */
    @Override
    public abstract int getSeasonalEffort();

    /**
     * Advances the cycle
     */
    @Override
    public abstract void next();

}
