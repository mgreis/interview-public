package com.devexperts.account.repository;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findByAccountKey(AccountKey accountKey);

    Account findById(long id);

}
