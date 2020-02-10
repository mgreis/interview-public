package com.devexperts.service;

import com.devexperts.account.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


public class AccountServiceImplTest {

    AccountServiceImpl victim;

    Account sourceAccount;

    Account targetAccount;

    @BeforeEach
    public void setup() {
        sourceAccount = mock(Account.class);
        targetAccount = mock(Account.class);
        victim = new AccountServiceImpl();
    }


    /**
     * This test can only infer whether or not an interaction occurred.
     * As the class is badly implemented, it is really hard to test much else.
     */
    @Test
    public void testSuccessfulTransfer() {

        double amount = 200.0;

        when(sourceAccount.getBalance()).thenReturn(1000.0);
        when(targetAccount.getBalance()).thenReturn(200.0);
        doNothing().when(sourceAccount).setBalance(800.0);
        doNothing().when(targetAccount).setBalance(400.0);

        victim.transfer(sourceAccount, targetAccount, amount);

        verify(sourceAccount).getBalance();
        verify(sourceAccount).setBalance(800.0);
        verify(targetAccount).getBalance();
        verify(targetAccount).setBalance(400.0);

        verifyNoMoreInteractions(sourceAccount, targetAccount);
    }

    /**
     * This test can only infer whether or not an interaction occurred.
     * As the class is badly implemented, it is really hard to test much else.
     */
    @Test
    public void testUnsuccessfulTransfer() {

        double amount = 200.0;

        when(sourceAccount.getBalance()).thenReturn(0.0);
        when(targetAccount.getBalance()).thenReturn(200.0);

        victim.transfer(sourceAccount, targetAccount, amount);

        verify(sourceAccount).getBalance();

        verifyNoMoreInteractions(sourceAccount, targetAccount);
    }
}
