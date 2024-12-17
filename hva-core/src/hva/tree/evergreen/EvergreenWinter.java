package hva.tree.evergreen;

import hva.util.Season;
import hva.tree.Tree;
import hva.util.Visitor;

import java.io.Serial;

public class EvergreenWinter extends EvergreenSeasonalCycle {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /**
     * @param tree
     */
    public EvergreenWinter(Tree tree) {
        super(tree, Season.WINTER);
    }

    /**
     * @return the seasonal effort to clean a tree
     * (Depends on the tree type and current season)
     */
    @Override
    public int getSeasonalEffort() {
        return 2;
    }

    /**
     * Advances the cycle
     */
    @Override
    public void next() {
        getTree().setSeasonalCycle(new EvergreenSpring(getTree()));
    }

    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
