package com.devexperts.service;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.account.repository.AccountKeyRepository;
import com.devexperts.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AlternativeAccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlternativeAccountServiceImpl.class);

    protected AccountKeyRepository accountKeyRepository;

    protected AccountRepository accountRepository;


    public AlternativeAccountServiceImpl(AccountKeyRepository accountKeyRepository,
                                         AccountRepository accountRepository) {

        this.accountKeyRepository = accountKeyRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * The big red button. It either works or gets rolled back. Thank god for transactional.
     * Since the method returns nothing there is no real way of knowing whether or not it worked.
     */
    @Override
    @Transactional
    public void clear() {
        accountKeyRepository.deleteAll();
        accountRepository.deleteAll();
        LOGGER.info("class=AlternativeAccountServiceImpl method=clear message='database cleared.'");
    }

    /**
     * Create an account. Since the method returns nothing there is no real way of knowing whether or not it worked.
     *
     * @param account account entity to add or update
     */
    @Transactional
    @Override
    public void createAccount(Account account) {
        if (account == null) {
            LOGGER.info("class=AlternativeAccountServiceImpl method=createAccount message='The account is null.'");
            return;
        }
        accountRepository.save(account);
    }

    /**
     * Get account using its id.
     *
     * @param id identification of an account to search for
     * @return an account or null. Returning null is not desirable, we can do so much better.
     */
    @Override
    public Account getAccount(long id) {
        List<AccountKey> accountKeyList = accountKeyRepository.findByAccountId(id);
        if (accountKeyList.size() == 1) {
            List<Account> accountList = accountRepository.findByAccountKey(accountKeyList.get(0));
            if (accountList.size() == 1) {
                return accountList.get(0);
            } else {
                LOGGER.info("class=AlternativeAccountServiceImpl method=getAccount accountId={} message='{} accounts returned'",
                        id,
                        accountList.size());
                return null;
            }
        } else {
            LOGGER.info("class=AlternativeAccountServiceImpl method=getAccountExtended accountId={} message='{} account keys returned'",
                    id,
                    accountKeyList.size());
            return null;
        }
    }

    /**
     * Transfer money from one account to another. Since it returns nothing, we have no real way of knowing if it went through.
     *
     * @param source account to transfer money from
     * @param target account to transfer money to
     * @param amount dollar amount to transfer
     */
    @Override
    @Transactional
    public void transfer(Account source, Account target, double amount) {
        if (isInvalidTransfer(source.getBalance(), amount)) return;

        source.setBalance(source.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);

    }

    protected boolean isInvalidTransfer(double sourceBalance, double amount) {
        return sourceBalance < 0 || amount < 0 || sourceBalance < amount;
    }
}
