package homework;


import java.util.ArrayList;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    private final ArrayList<Customer> list = new ArrayList<>();


    public void add(Customer customer) {
        list.add(customer);
    }

    public Customer take() {
        Customer customer = list.get(list.size() - 1);
        list.remove(customer);
        return customer;
    }
}
