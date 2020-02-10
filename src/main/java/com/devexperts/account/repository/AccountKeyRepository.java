package com.devexperts.account.repository;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountKeyRepository extends CrudRepository<AccountKey, Long> {

    List<AccountKey> findByAccountId(long accountId);

    Account findById(long id);

}
