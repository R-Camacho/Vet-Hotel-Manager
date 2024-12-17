package hva.tree;

import hva.util.Season;
import hva.util.Visitable;

import java.io.Serial;
import java.io.Serializable;

public abstract class SeasonalCycle implements Serializable, Visitable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The cycle current season. */
    private Season _season;

    /** The tree associated with the cycle. */
    private final Tree _tree;

    public SeasonalCycle(Tree tree) {
        _tree = tree;
    }

    /**
     * @return the seasonal effort to clean a tree
     * (Depends on the tree type and current season)
     */
    public abstract int getSeasonalEffort();

    /**
     * @return The current season
     */
    public final Season getSeason() { return _season; }

    /**
     * @param season season
     */
    protected final void setSeason(Season season) { _season = season; }

    /**
     * Advances the cycle
     */
    public abstract void next();

    /**
     * @return The tree
     */
    public final Tree getTree() { return _tree; }

}
