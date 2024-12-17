package hva.habitat;

public class Influence {

    /** Polarity is neutral by default */
    private InfluencePolarity _polarity = InfluencePolarity.NEU;

    public Influence() {
        // empty: default
    }

    public Influence(InfluencePolarity polarity) {
        _polarity = polarity;
    }

    public int getPolarity() {
        return _polarity.getValue();
    }

}
