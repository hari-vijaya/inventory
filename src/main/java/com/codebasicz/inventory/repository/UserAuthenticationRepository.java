package com.codebasicz.inventory.repository;

import com.codebasicz.inventory.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Integer> {
    Optional<UserAuthentication> findByUsername(String username);

    Optional<UserAuthentication> findByPassword(String oldPassword);
}
