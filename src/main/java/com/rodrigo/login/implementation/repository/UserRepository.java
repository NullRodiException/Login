package com.rodrigo.login.implementation.repository;

import com.rodrigo.login.implementation.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Email is invalid") String email);

    boolean existsByUsername(@NotBlank(message = "Username is required") @Pattern(regexp = "^[a-zA-Z0-9_-]{3,15}$", message = "Username must be alphanumeric and between 3 and 15 characters") String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("delete from User u where u.id = :id")
    void removeUserById(UUID id);

    @Query("select u from User u " +
            "where (u.username = :login or u.email = :login) ")
    Optional<User> findByLogin(@Param("login") String login);
}
