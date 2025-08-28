package com.dataaccess.repositories;

import com.dataaccess.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("FROM User u WHERE u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);
}
