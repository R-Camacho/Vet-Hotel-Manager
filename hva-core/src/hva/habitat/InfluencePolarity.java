package hva.habitat;

public enum InfluencePolarity {

    POS(20),

    NEU(0),

    NEG(-20);

    /** Value associated the influence. */
    private final int _value;

    InfluencePolarity(int value) {
        _value = value;
    }

    public int getValue() {
        return _value;
    }

}
