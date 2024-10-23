package datastore;

import model.issues.CustomerIssue;
import model.users.Customer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerStore {
    Set<Customer> customerList;

    public CustomerStore() {
        this.customerList = new HashSet<>();
    }

    public void addCustomer(final Customer customer) {
        this.customerList.add(customer);
    }

}
