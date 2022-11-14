package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MallAtmTest {
    static Atm mallAtm;

    @BeforeEach
    void setup() {
        mallAtm = new MallAtm();
    }

    @Test

    @DisplayName("Статистика банкомата с деньгами.")
    void printAvailableMoneyStatistic1() {
        mallAtm.putBanknotesBox(new BanknotesBox(Banknotes.D500, 5));
        Assertions.assertEquals("""
                        ATM contain:
                        5 banknotes with denomination 500
                        Total = 2500""",
                mallAtm.getAvailableMoneyStatistic());
    }

    @Test
    @DisplayName("Статистика пустого банкомата.")
    void printAvailableMoneyStatistic2() {
        mallAtm.getAvailableMoneyStatistic();
        Assertions.assertEquals("""
                        ATM contain:
                        Total = 0""",
                mallAtm.getAvailableMoneyStatistic());

    }

    @Test
    @DisplayName("Кладём и снимаем по 500.")
    void getMoney1() {
        mallAtm.putBanknotesBox(new BanknotesBox(Banknotes.D500, 10));
        mallAtm.getMoney(1500);
        Assertions.assertEquals(7, mallAtm.getBanknotesStatistic().get(Banknotes.D500.getValue()));
    }

    @Test
    @DisplayName("Кладём и снимаем по 100.")
    void getMoney2() {
        mallAtm.putBanknotesBox(new BanknotesBox(Banknotes.D100, 20));
        mallAtm.getMoney(1500);
        Assertions.assertEquals(5, mallAtm.getBanknotesStatistic().get(Banknotes.D100.getValue()));
    }

    @Test
    @DisplayName("Кладём 100 и 500. Купюр 500 мало, снимаем 100.")
    void getMoney3() {
        mallAtm.putBanknotesBox(new BanknotesBox(Banknotes.D100, 20));
        mallAtm.putBanknotesBox(new BanknotesBox(Banknotes.D500, 1));
        mallAtm.getMoney(1000);
        Assertions.assertEquals(1, mallAtm.getBanknotesStatistic().get(Banknotes.D500.getValue()));
        Assertions.assertEquals(10, mallAtm.getBanknotesStatistic().get(Banknotes.D100.getValue()));
    }

    @Test
    @DisplayName("Кладём одинаковую коробку с купюрами дважды.")
    void putBanknotesBox() {
        mallAtm.putBanknotesBox(new BanknotesBox(Banknotes.D100, 20));
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class,
                () -> mallAtm.putBanknotesBox(new BanknotesBox(Banknotes.D100, 20)));

        Assertions.assertEquals("Can't put BanknotesBox in ATM. BanknotesBox with denomination 100 installed.", runtimeException.getMessage());
    }

    @Test
    @DisplayName("Удаляем отсутствующую коробку с купюрами.")
    void removeBanknotesBox() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class,
                () -> mallAtm.removeBanknotesBox(Banknotes.D2000));
        Assertions.assertEquals("Can't put BanknotesBox in ATM. BanknotesBox with denomination 2000 doesn't exist.", runtimeException.getMessage());
    }
}