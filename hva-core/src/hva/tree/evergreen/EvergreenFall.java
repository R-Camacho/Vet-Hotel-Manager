package hva.tree.evergreen;

import hva.util.Season;
import hva.tree.Tree;
import hva.util.Visitor;

import java.io.Serial;

public class EvergreenFall extends EvergreenSeasonalCycle {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /**
     * @param tree
     */
    public EvergreenFall(Tree tree) {
        super(tree, Season.FALL);
    }

    /**
     * @return the seasonal effort to clean a tree
     * (Depends on the tree type and current season)
     */
    @Override
    public int getSeasonalEffort() {
        return 1;
    }

    /**
     * Advances the cycle
     */
    @Override
    public void next() {
        getTree().setSeasonalCycle(new EvergreenWinter(getTree()));
    }

    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}