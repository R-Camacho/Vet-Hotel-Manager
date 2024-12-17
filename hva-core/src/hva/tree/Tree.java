package hva.tree;

import hva.util.Season;
import hva.util.Visitable;
import hva.util.Visitor;

import java.io.Serial;
import java.io.Serializable;

public abstract class Tree implements Serializable, Visitable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The tree key. */
    private final String _key;

    /** The tree name. */
    private final String _name;

    /** The tree age. */
    private int _age;

    /** The tree base cleaning difficulty. */
    private final int _baseDifficulty;

    /** The seasonal cycle associated with the tree. */
    private SeasonalCycle _seasonalCycle;

    /** When was this tree planted. */
    private final Season _startingSeason;

    /**
     * @param key
     * @param name
     * @param age
     * @param baseDifficulty
     */
    public Tree
    (String key, String name, int age, int baseDifficulty, Season season) {
        _key = key;
        _name = name;
        _age = age;
        _baseDifficulty = baseDifficulty;
        _startingSeason = season;
    }

    /**
     * @return the tree key
     */
    public final String getKey() { return _key; }

    /**
     * @return the tree name
     */
    public final String getName() { return _name; }

    /**
     * @return the tree age
     */
    public final int getAge() { return _age; }

    /**
     * @return the tree base cleaning difficulty
     */
    public final int getBaseDifficulty() { return _baseDifficulty; }

    /**
     * @return the full difficulty for a tree to be cleaned
     */
    public double getDifficulty() {
        return getBaseDifficulty() * getSeasonalEffort() * Math.log(_age + 1);
    }

    /**
     * @return the seasonal effort to clean a tree
     * (Depends on the tree type and current season)
     */
    protected int getSeasonalEffort() {
        return _seasonalCycle.getSeasonalEffort();
    }

    public void setSeasonalCycle(SeasonalCycle seasonalCycle) {
        _seasonalCycle = seasonalCycle;
    }

    /**
     * @return the tree seasonalCycle
     */
    public final SeasonalCycle getSeasonalCycle() { return _seasonalCycle; }

    /**
     * Ages 1 season
     */
    public void ageSeason() {
        _seasonalCycle.next();
        if (_seasonalCycle.getSeason() == _startingSeason)
            _age++;
    }

    /** @see hva.util.Visitable#accept(Visitor)  */
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
