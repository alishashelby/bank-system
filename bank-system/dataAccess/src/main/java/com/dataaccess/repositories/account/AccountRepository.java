package com.dataaccess.repositories.account;

import com.dataaccess.entities.Account;
import com.dataaccess.entities.Transaction;
import com.dataaccess.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Public class AccountRepository represents in-memory repository of accounts.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query("FROM Account a WHERE a.accountId = :accountId")
    Optional<Account> findByAccountId(@Param("accountId") int accountId);

    @Query("SELECT a.accountId as accountId FROM Account a WHERE a.user.id = :userId")
    Collection<Integer> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT a.accountId as accountId FROM Account a")
    Collection<Integer> findAllAccountIds();

    @Query("FROM Transaction t WHERE (:account IS NULL OR t.account = :account) " +
            "AND (:type IS NULL OR t.type = :type)")
    Collection<Transaction> findAllTransactionsByAccountAndType(@Param("account") Account account,
                                                                  @Param("type") TransactionType type);
}
