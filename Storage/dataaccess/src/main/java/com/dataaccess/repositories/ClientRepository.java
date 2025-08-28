package com.dataaccess.repositories;

import com.dataaccess.entities.ClientEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<ClientEvent, String> {
}
