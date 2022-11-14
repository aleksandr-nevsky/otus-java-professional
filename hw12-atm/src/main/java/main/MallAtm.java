package main;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Класс банкомата.
 */
public class MallAtm implements Atm {
    // Установленные коробки с купюрами.
    private final Map<Integer, BanknotesBox> banknotesBoxMap = new HashMap<>();
    // Номиналы имеющихся купюр.
    private final SortedSet<Integer> denominationSet = new TreeSet<>();

    /**
     * Кладём коробку с купюрами в банкомат.
     *
     * @param banknotesBox коробка с купюрами.
     */
    @Override
    public void putBanknotesBox(BanknotesBox banknotesBox) {
        if (banknotesBoxMap.containsKey(banknotesBox.getBanknotesDenomination())) {
            throw new RuntimeException("Can't put BanknotesBox in ATM. BanknotesBox with denomination " + banknotesBox.getBanknotesDenomination() + " installed.");
        }
        banknotesBoxMap.put(banknotesBox.getBanknotesDenomination(), banknotesBox);
        denominationSet.add(banknotesBox.getBanknotesDenomination());
    }

    /**
     * Удаляем коробку с купюрами из банкомата.
     *
     * @param banknotes наминал купюр для удаления коробки.
     */
    @Override
    public void removeBanknotesBox(Banknotes banknotes) {
        if (banknotesBoxMap.remove(banknotes.getDenomination()) == null) {
            throw new RuntimeException("Can't put BanknotesBox in ATM. BanknotesBox with denomination " + banknotes.getDenomination() + " doesn't exist.");
        } else {
            denominationSet.remove(banknotes.getDenomination());
        }
    }

    /**
     * Получить деньги.
     *
     * @param sum Сумма денег.
     */
    @Override
    public void getMoney(int sum) {
        int neededBanknotes;

        for (Integer denomination : denominationSet) {
            if (sum % denomination == 0) {
                neededBanknotes = sum / denomination;
                if (isBoxContainBanknotes(denomination, neededBanknotes)) {
                    banknotesBoxMap.get(denomination).removeBanknotes(neededBanknotes);
                    System.out.println("ATM spit out " + sum);
                    return;
                }
            }
        }
        throw new RuntimeException("ATM has no money.");
    }

    /**
     * Получаем статистику по имеющимся деньгам.
     *
     * @return Строка со статистикой.
     */
    @Override
    public String getAvailableMoneyStatistic() {
        StringBuilder result = new StringBuilder();
        int total = 0;
        result.append("ATM contain:\n");
        for (BanknotesBox banknotesBox : banknotesBoxMap.values()) {
            result.append(String.format("%d banknotes with denomination %d%n", banknotesBox.getBanknotesCount(), banknotesBox.getBanknotesDenomination()));
            total = total + (banknotesBox.getBanknotesCount() * banknotesBox.getBanknotesDenomination());
        }

        result.append(String.format("Total = %d", total));
        return result.toString();
    }

    /**
     * Получаем Map с номиналами купюр и их количеством.
     *
     * @return Map с номиналами купюр и их количеством.
     */
    @Override
    public Map<Integer, Integer> getBanknotesStatistic() {
        Map<Integer, Integer> result = new HashMap<>();
        banknotesBoxMap.forEach((k, v) -> result.put(v.getBanknotesDenomination(), v.getBanknotesCount()));

        return result;
    }

    /**
     * Проверяем есть ли в коробке с купюрами нужное количество купюр.
     *
     * @param denomination  Номинал купюр.
     * @param needBanknotes Нужное количество купюр.
     * @return Если есть нужное количество - true. Иначе false.
     */
    private boolean isBoxContainBanknotes(int denomination, int needBanknotes) {
        return banknotesBoxMap.get(denomination).getBanknotesCount() >= needBanknotes;
    }
}
