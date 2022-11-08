package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AtmTest {
    static Atm atm;

    @BeforeEach
    void setup() {
        atm = new Atm();
    }

    @Test

    @DisplayName("Статистика банкомата с деньгами.")
    void printAvailableMoneyStatistic1() {
        atm.putBanknotesBox(new BanknotesBox(Banknotes.D500, 5));
        Assertions.assertEquals("""
                        ATM contain:
                        5 banknotes with denomination 500
                        Total = 2500""",
                atm.getAvailableMoneyStatistic());
    }

    @Test
    @DisplayName("Статистика пустого банкомата.")
    void printAvailableMoneyStatistic2() {
        atm.getAvailableMoneyStatistic();
        Assertions.assertEquals("""
                        ATM contain:
                        Total = 0""",
                atm.getAvailableMoneyStatistic());

    }

    @Test
    @DisplayName("Кладём и снимаем по 500.")
    void getMoney1() {
        atm.putBanknotesBox(new BanknotesBox(Banknotes.D500, 10));
        atm.getMoney(1500);
        Assertions.assertEquals(7, atm.getBanknotesStatistic().get(Banknotes.D500.getValue()));
    }

    @Test
    @DisplayName("Кладём и снимаем по 100.")
    void getMoney2() {
        atm.putBanknotesBox(new BanknotesBox(Banknotes.D100, 20));
        atm.getMoney(1500);
        Assertions.assertEquals(5, atm.getBanknotesStatistic().get(Banknotes.D100.getValue()));
    }

    @Test
    @DisplayName("Кладём 100 и 500. Купюр 500 мало, снимаем 100.")
    void getMoney3() {
        atm.putBanknotesBox(new BanknotesBox(Banknotes.D100, 20));
        atm.putBanknotesBox(new BanknotesBox(Banknotes.D500, 1));
        atm.getMoney(1000);
        Assertions.assertEquals(1, atm.getBanknotesStatistic().get(Banknotes.D500.getValue()));
        Assertions.assertEquals(10, atm.getBanknotesStatistic().get(Banknotes.D100.getValue()));
    }

    @Test
    @DisplayName("Кладём одинаковую коробку с купюрами дважды.")
    void putBanknotesBox() {
        atm.putBanknotesBox(new BanknotesBox(Banknotes.D100, 20));
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class,
                () -> atm.putBanknotesBox(new BanknotesBox(Banknotes.D100, 20)));

        Assertions.assertEquals("Can't put BanknotesBox in ATM. BanknotesBox with denomination 100 installed.", runtimeException.getMessage());
    }

    @Test
    @DisplayName("Удаляем отсутствующую коробку с купюрами.")
    void removeBanknotesBox() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class,
                () -> atm.removeBanknotesBox(Banknotes.D2000));
        Assertions.assertEquals("Can't put BanknotesBox in ATM. BanknotesBox with denomination 2000 doesn't exist.", runtimeException.getMessage());
    }
}