package com.porto.devcatalog.Porto.repositories;

import com.porto.devcatalog.Porto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
