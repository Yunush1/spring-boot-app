package com.service.beta.repository;

import com.service.beta.data.model.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<DbUser, Long> {
    DbUser findByUsername(String username);
    DbUser findByEmail(String email);


}
