package com.dataaccess.repositories.user;

import com.dataaccess.entities.User;
import com.dataaccess.enums.Gender;
import com.dataaccess.enums.HairColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Public class UserRepository represents in-memory repository of users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("FROM User u WHERE u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Query("FROM User u WHERE u.id = :id")
    Optional<User> findById(@Param("id") UUID id);

    @Query("SELECT u.login AS login FROM User u WHERE (:hairColor IS NULL OR u.hairColor = :hairColor) " +
            "AND (:gender IS NULL OR u.gender = :gender)")
    Collection<String> findByHairColorAndGender(@Param("hairColor") HairColor hairColor,
                                                @Param("gender") Gender gender);
}
