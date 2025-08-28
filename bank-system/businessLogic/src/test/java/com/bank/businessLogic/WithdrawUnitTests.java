package com.bank.businessLogic;

import com.businesslogic.exceptions.account.InsufficientBalanceException;
import com.businesslogic.services.KafkaProducer;
import com.businesslogic.services.account.AccountService;
import com.dataaccess.entities.Account;
import com.dataaccess.entities.User;
import com.dataaccess.enums.Gender;
import com.dataaccess.enums.HairColor;
import com.dataaccess.repositories.account.AccountRepository;
import com.dataaccess.repositories.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WithdrawUnitTests {
    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        accountService = new AccountService(
                accountRepository,
                userRepository,
                Mockito.mock(KafkaProducer.class));
    }

    @Test
    public void withdraw_should_return_success_when_withdraw_is_valid() {
        var user = new User("testUser", "test", 20, Gender.FEMALE, HairColor.DARK_BLOND);
        var account = new Account(123456, user);
        account.deposit(1000);

        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(accountRepository.findByAccountId(account.getId())).thenReturn(Optional.of(account));

        Assertions.assertDoesNotThrow(() -> accountService.withdraw(account.getId(), user.getLogin(), 1000));

        Assertions.assertEquals(0, account.getBalance());
        verify(accountRepository).save(account);
    }

    @Test
    public void withdraw_should_return_failure_when_withdraw_is_invalid() {
        var user = new User("testUser", "test", 20, Gender.FEMALE, HairColor.DARK_BLOND);
        var account = new Account(123456, user);
        account.deposit(1000);

        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(accountRepository.findByAccountId(account.getId())).thenReturn(Optional.of(account));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            accountService.withdraw(account.getId(), user.getLogin(), 1001);
        });

        Assertions.assertEquals(1000, account.getBalance());
        verify(accountRepository, Mockito.never()).save(account);
    }
}
