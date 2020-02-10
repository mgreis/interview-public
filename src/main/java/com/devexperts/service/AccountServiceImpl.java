package com.devexperts.service;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    //Not thread safe
    //a private resource inside a class is hard to test
    // a private resource inside class is tightly coupled to that class. we will not be able to use the resource
    //without instantiating this class.
    //this resource should be injected instead.
    private final List<Account> accounts = new ArrayList<>();


    //Are we even sure we want to erase all accounts or just one?
    //Do we even have a way of telling the client if this operation went smoothly?
    @Override
    public void clear() {
        accounts.clear();
    }

    //Do we even have a way of telling the client if this operation went smoothly?
    //if this operation is called twice for the same account we will end up with two accounts
    @Override
    public void createAccount(Account account) {
        accounts.add(account);
    }

    //This will not work the although the id is the same filter always compares two different objects.
    //Streaming lists might sound like a good idea because the operation can be paralellized
    // but its not. There are much more effective ways of finding an item in a list like using
    // for instance an hashset instead of an array list.
    //If we accidentally insert several instances of the same account to the list, find any will return the first it finds.
    //This will be a mess as we have no guaranty we will returning the same account instance.
    //Returning null is not advisable. Use Optional instead.
    @Override
    public Account getAccount(long id) {
        return accounts.stream()
                .filter(account -> account.getAccountKey() == AccountKey.valueOf(id))
                .findAny()
                .orElse(null);
    }

    // TASK 2 transfer.
    // NOT thread safe!!!!
    // This is really bad, we cannot guaranty even if the transaction went through without checking the accounts as the method returns void.
    @Override
    public void transfer(Account source, Account target, double amount) {
        double sourceBalance = source.getBalance();
        if (isValidTransfer(amount, sourceBalance)) {
            source.setBalance(sourceBalance - amount);
            target.setBalance(target.getBalance() + amount);
        }
    }

    private boolean isValidTransfer(double amount, double sourceBalance) {
        return amount >= 0 && sourceBalance >= 0 && sourceBalance >= amount;
    }
}
