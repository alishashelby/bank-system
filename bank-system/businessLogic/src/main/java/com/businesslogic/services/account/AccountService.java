package com.businesslogic.services.account;

import com.businesslogic.dto.TransactionDTO;
import com.businesslogic.dto.kafka.KafkaEvent;
import com.businesslogic.dto.kafka.UpdateDTO;
import com.businesslogic.exceptions.*;
import com.businesslogic.exceptions.account.*;
import com.businesslogic.exceptions.user.UserNotFoundException;
import com.businesslogic.services.KafkaProducer;
import com.dataaccess.entities.Account;
import com.dataaccess.entities.User;
import com.dataaccess.enums.KafkaEventType;
import com.dataaccess.enums.TransactionType;
import com.dataaccess.repositories.account.AccountRepository;
import com.dataaccess.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Public class AccountService represents service to work with user's accounts.
 */
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;

    /**
     * Initializes a new instance of the AccountService class.
     * @param accountRepository the in-memory repository from DAL.
     * @param userRepository the in-memory repository from DAL.
     */
    @Autowired
    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository,
                          KafkaProducer kafkaProducer) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * Void method to create account.
     * @param id the id of Account - int.
     * @param userLogin the user's login who account belongs to - String.
     * @throws BLException UserNotFoundException or AccountExistsException.
     */
    @Transactional
    public void createAccount(int id, String userLogin) throws BLException {
        Optional<User> optionalUser = userRepository.findByLogin(userLogin);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userLogin);
        }
        User user = optionalUser.get();

        if (accountRepository.findByAccountId(id).isPresent()) {
            throw new AccountExistsException(id);
        }

        var account = new Account(id, user);
        user.addAccount(account);

        userRepository.save(user);

        kafkaProducer.sendAccountMessage(new KafkaEvent(((Integer) account.getId()).toString(),
                KafkaEventType.ACCOUNT_CREATED.toString(), account));
    }

    /**
     * Gets balance of account.
     * @param id the id of Account - int.
     * @param userLogin the user's login who account belongs to - String.
     * @return double - the balance.
     * @throws BLException UserNotFoundException or AccountNotFoundException.
     */
    public double getBalance(int id, String userLogin) throws BLException {
        Optional<User> optionalUser = userRepository.findByLogin(userLogin);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userLogin);
        }

        Optional<Account> optionalAccount = accountRepository.findByAccountId(id);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(id);
        }
        var account = optionalAccount.get();
        if (!account.getUser().getLogin().equals(userLogin)) {
            throw new AccountNotFoundException(id);
        }

        return account.getBalance();
    }

    /**
     * Void method to withdraw money on account.
     * @param id the id of Account - int.
     * @param userLogin the user's login who account belongs to - String.
     * @param amount the amount of money which is wanted to withdraw.
     * @throws BLException maybe thrown:
     * UserNotFoundException
     * NonPositiveAmountException
     * AccountNotFoundException
     * InsufficientBalanceException
     */
    @Transactional
    public void withdraw(int id, String userLogin, double amount) throws BLException {
        Optional<User> optionalUser = userRepository.findByLogin(userLogin);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userLogin);
        }

        if (amount <= 0) {
            throw new NonPositiveAmountException(amount);
        }

        Optional<Account> optionalAccount = accountRepository.findByAccountId(id);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(id);
        }
        Account account = optionalAccount.get();
        if (!account.getUser().getLogin().equals(userLogin)) {
            throw new AccountNotFoundException(id);
        }

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException(amount);
        }

        account.withdraw(amount);
        accountRepository.save(account);

        kafkaProducer.sendAccountMessage(new KafkaEvent(((Integer) account.getId()).toString(),
                KafkaEventType.ACCOUNT_UPDATED.toString(),
                new UpdateDTO(TransactionType.WITHDRAW.toString(), ((Double) amount).toString())));
    }

    /**
     * Void method to deposit money on account.
     * @param id the id of Account - int.
     * @param userLogin the user's login who account belongs to - String.
     * @param amount the amount of money which is wanted to deposit.
     * @throws BLException maybe thrown:
     * UserNotFoundException
     * NonPositiveAmountException
     * AccountNotFoundException
     */
    @Transactional
    public void deposit(int id, String userLogin, double amount) throws BLException {
        Optional<User> optionalUser = userRepository.findByLogin(userLogin);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userLogin);
        }

        if (amount <= 0) {
            throw new NonPositiveAmountException(amount);
        }

        Optional<Account> optionalAccount = accountRepository.findByAccountId(id);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(id);
        }
        Account account = optionalAccount.get();
        if (!account.getUser().getLogin().equals(userLogin)) {
            throw new AccountNotFoundException(id);
        }

        account.deposit(amount);
        accountRepository.save(account);

        kafkaProducer.sendAccountMessage(new KafkaEvent(((Integer) account.getId()).toString(),
                KafkaEventType.ACCOUNT_UPDATED.toString(),
                new UpdateDTO(TransactionType.DEPOSIT.toString(), ((Double) amount).toString())));
    }

    /**
     * Void method to transfer money from one account to another one.
     * @param fromId the id of Account which id transferred from - int.
     * @param userLogin the user's login who account belongs to - String.
     * @param toId the id of Account which id transferred to - int.
     * @param amount the amount of money which is wanted to transfer.
     * @throws BLException maybe thrown:
     * UserNotFoundException
     * AccountNotFoundException
     * NonPositiveAmountException
     * InsufficientBalanceException
     * TransferSameAccountException
     */
    @Transactional
    public void transfer(int fromId, String userLogin, int toId, double amount) throws BLException {
        var optionalUser = userRepository.findByLogin(userLogin);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userLogin);
        }
        var fromUser = optionalUser.get();

        Optional<Account> optionalFrom = accountRepository.findByAccountId(fromId);
        if (optionalFrom.isEmpty()) {
            throw new AccountNotFoundException(fromId);
        }
        var fromAccount = optionalFrom.get();
        if (!fromAccount.getUser().getLogin().equals(userLogin)) {
            throw new AccountNotFoundException(fromId);
        }

        if (amount <= 0) {
            throw new NonPositiveAmountException(amount);
        }

        if (fromAccount.getBalance() < amount) {
            throw new InsufficientBalanceException(amount);
        }

        Optional<Account> optionalTo = accountRepository.findByAccountId(toId);
        if (optionalTo.isEmpty()) {
            throw new AccountNotFoundException(toId);
        }

        Account toAccount = optionalTo.get();

        if (fromAccount.getId() == toAccount.getId()) {
            throw new TransferSameAccountException(fromAccount.getId());
        }

        String toUserLogin = toAccount.getUser().getLogin();
        Optional<User> optionalToUser = userRepository.findByLogin(toUserLogin);
        if (optionalToUser.isEmpty()) {
            throw new UserNotFoundException(toUserLogin);
        }
        User toUser = optionalToUser.get();

        double overPay = amount * calculateOverPay(fromUser, toUser);
        double total = overPay + amount;

        if (fromAccount.getBalance() < total) {
            throw new InsufficientBalanceException(total);
        }

        fromAccount.withdraw(total, TransactionType.TRANSFER_TO);
        accountRepository.save(fromAccount);

        toAccount.deposit(amount, TransactionType.TRANSFER_FROM);
        accountRepository.save(toAccount);

        kafkaProducer.sendAccountMessage(new KafkaEvent(((Integer) fromAccount.getId()).toString(),
                KafkaEventType.ACCOUNT_UPDATED.toString(),
                new UpdateDTO(TransactionType.TRANSFER_FROM.toString(), ((Double) amount).toString())));
        kafkaProducer.sendAccountMessage(new KafkaEvent(((Integer) toAccount.getId()).toString(),
                KafkaEventType.ACCOUNT_UPDATED.toString(),
                new UpdateDTO(TransactionType.TRANSFER_TO.toString(), ((Double) amount).toString())));
    }

    private double calculateOverPay(User fromUser, User toUser) {
        if (fromUser.getLogin().equals(toUser.getLogin())) {
            return 0;
        }

        if (fromUser.isFriend(toUser)) {
            return 0.03;
        }

        return 0.1;
    }

    public Collection<Integer> getAllByUserId(UUID userId) throws BLException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userId.toString());
        }

        return accountRepository.findByUserId(userId);
    }

    public Collection<Integer> getAllAccountIds() {
        return accountRepository.findAllAccountIds();
    }

    public Collection<TransactionDTO> getTransactionsFiltered(Integer accountId, TransactionType type) throws BLException {
        if (accountId == null) {
            return accountRepository.findAllTransactionsByAccountAndType(null, type)
                    .stream().map(TransactionDTO::new).toList();
        }

        Optional<Account> optionalAccount = accountRepository.findByAccountId(accountId);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(accountId);
        }

        return accountRepository.findAllTransactionsByAccountAndType(optionalAccount.get(), type)
                .stream().map(TransactionDTO::new).toList();
    }
}