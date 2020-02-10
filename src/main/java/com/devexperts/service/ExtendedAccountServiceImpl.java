package com.devexperts.service;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.account.repository.AccountKeyRepository;
import com.devexperts.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("com.devexperts.service.extendedAccountServiceImpl")
public class ExtendedAccountServiceImpl extends AlternativeAccountServiceImpl implements ExtendedAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedAccountServiceImpl.class);


    public ExtendedAccountServiceImpl(AccountKeyRepository accountKeyRepository,
                                      AccountRepository accountRepository) {
        super(accountKeyRepository, accountRepository);
    }

    @Override
    @Transactional
    public Optional<Account> createAccountExtended(Account account) {
        if (account == null) {
            LOGGER.info("class=ExtendedAccountServiceImpl method=createAccountExtended message='The account is null.'");
            return Optional.empty();
        }
        return Optional.of(accountRepository.save(account));
    }

    @Override
    public Optional<Account> getAccountExtended(long id) {

        List<AccountKey> accountKeyList = accountKeyRepository.findByAccountId(id);

        if (accountKeyList.size() == 1) {
            List<Account> accountList = accountRepository.findByAccountKey(accountKeyList.get(0));
            if (accountList.size() == 1) {
                return Optional.of(accountList.get(0));
            } else {
                LOGGER.info("class=ExtendedAccountServiceImpl method=getAccountExtended accountId={} message='{} accounts returned'",
                        id,
                        accountList.size());
                return Optional.empty();
            }
        } else {
            LOGGER.info("class=ExtendedAccountServiceImpl method=getAccountExtended accountId={} message='{} account keys returned'",
                    id,
                    accountKeyList.size());
            return Optional.empty();
        }

    }

    @Transactional
    @Override
    public boolean transferExtended(Account sourceAccount, Account targetAccount, double amount) {
        double sourceAccountBalance = sourceAccount.getBalance();
        if (super.isInvalidTransfer(sourceAccountBalance, amount)) {
            LOGGER.info("class=ExtendedAccountServiceImpl method=transferExtended sourceAccountBalance={}, transferAmount={}  message='Invalid transfer amounts'",
                    sourceAccountBalance,
                    amount);
            return false;
        }
        sourceAccount.setBalance(sourceAccountBalance - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);
        return true;
    }
}
