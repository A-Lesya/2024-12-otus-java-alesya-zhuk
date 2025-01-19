package homework;

import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>((o1, o2) -> {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        return Long.compare(o1.getScores(), o2.getScores());
    });

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
