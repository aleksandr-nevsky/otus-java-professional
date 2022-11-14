package main;

import java.util.Map;

/**
 * Интерфейс для банкоматов.
 */
public interface Atm {

    void putBanknotesBox(BanknotesBox banknotesBox);

    void removeBanknotesBox(Banknotes banknotes);

    void getMoney(int sum);

    String getAvailableMoneyStatistic();

    Map<Integer, Integer> getBanknotesStatistic();
}
