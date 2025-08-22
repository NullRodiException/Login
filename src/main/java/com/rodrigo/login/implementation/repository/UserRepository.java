package com.rodrigo.login.implementation.repository;

import com.rodrigo.login.implementation.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByUsernameAndIdNot(String username, UUID id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("delete from User u where u.id = :id")
    void removeUserById(UUID id);

    @Query("select u from User u " +
            "where (u.username = :login or u.email = :login) ")
    Optional<User> findByLogin(@Param("login") String login);
}
