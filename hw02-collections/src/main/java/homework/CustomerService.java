package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        var entry = map.firstEntry();
        return copyEntry(entry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var entry = map.higherEntry(customer);
        return copyEntry(entry);
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> copyEntry(Map.Entry<Customer, String> entry) {
        if (entry == null) return null;
        var customer = entry.getKey();
        var customerCopy = new Customer(customer.getId(), customer.getName(), customer.getScores());
        return Map.entry(customerCopy, entry.getValue());
    }
}
