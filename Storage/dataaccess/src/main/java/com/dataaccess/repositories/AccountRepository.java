package com.dataaccess.repositories;

import com.dataaccess.entities.AccountEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<AccountEvent, String> {
}
