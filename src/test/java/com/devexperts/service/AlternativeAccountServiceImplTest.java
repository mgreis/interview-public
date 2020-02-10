package com.devexperts.service;

import com.devexperts.account.Account;
import com.devexperts.account.repository.AccountKeyRepository;
import com.devexperts.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AlternativeAccountServiceImplTest {

    AlternativeAccountServiceImpl victim;

    AccountKeyRepository accountKeyRepository;

    AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        accountKeyRepository = mock(AccountKeyRepository.class);
        accountRepository = mock(AccountRepository.class);
        victim = new AlternativeAccountServiceImpl(accountKeyRepository, accountRepository);
    }

    @Test
    public void testClear() {
        doNothing().when(accountKeyRepository).deleteAll();
        doNothing().when(accountRepository).deleteAll();

        victim.clear();

        verify(accountKeyRepository).deleteAll();
        verify(accountRepository).deleteAll();

        verifyNoMoreInteractions(accountKeyRepository, accountRepository);
    }

    @Test
    public void testCreateAccount() {
        Account account = mock(Account.class);
        doReturn(account).when(accountRepository).save(account);
        victim.createAccount(account);

        verify(accountRepository).save(account);

        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testCreateAccountWithNullAccountParameter() {
        victim.createAccount(null);

        verifyZeroInteractions(accountRepository);
    }
}
