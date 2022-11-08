package main;

/**
 * Коробка с деньгами.
 */
public class BanknotesBox {
    // Номинал купюры.
    private final int banknotesDenomination;
    // Количество купюр.
    private int banknotesCount;

    public BanknotesBox(Banknotes banknotesDenomination, int banknotesCount) {
        this.banknotesDenomination = banknotesDenomination.getDenomination();
        this.banknotesCount = banknotesCount;
    }

    public int getBanknotesDenomination() {
        return banknotesDenomination;
    }

    public int getBanknotesCount() {
        return banknotesCount;
    }

    /**
     * Вынимаем банкноты из коробки.
     *
     * @param count Количество банкнот для изъятия.
     */
    public void removeBanknotes(int count) {
        if (count > banknotesCount) {
            throw new RuntimeException("Wrong banknotes counter when remove.");
        }
        banknotesCount -= count;
    }

//    public void addBanknotes(int count) {
//        if (count < 1) {
//            throw new RuntimeException("Wrong banknotes counter when add.");
//        }
//        banknotesCount += count;
//    }
}
