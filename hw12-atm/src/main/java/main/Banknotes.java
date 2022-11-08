package main;

/**
 * Перечисление возможных банкнот.
 */
public enum Banknotes {
    D100(100),
    D200(200),
    D500(500),
    D1000(1000),
    D2000(2000),
    D5000(5000);

    private final int denomination;

    Banknotes(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    public int getValue() {
        return denomination;
    }
}
