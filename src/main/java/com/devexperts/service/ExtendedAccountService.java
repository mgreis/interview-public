package com.devexperts.service;

import com.devexperts.account.Account;

import java.util.Optional;

public interface ExtendedAccountService {

    Optional<Account> createAccountExtended(Account account);

    /**
     * Get account from the cache
     *
     * @param id identification of an account to search for
     * @return account associated with given id or {@code null} if account is not found in the cache
     */
    Optional<Account> getAccountExtended(long id);

    /**
     * Transfers given amount of money from source account to target account
     *
     * @param sourceAccount account to transfer money from
     * @param targetAccount account to transfer money to
     * @param amount        dollar amount to transfer
     */
    boolean transferExtended(Account sourceAccount, Account targetAccount, double amount);


}
