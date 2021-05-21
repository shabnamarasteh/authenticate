package com.learn.authenticate.modules.users.model.repository;

import com.learn.authenticate.modules.users.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmailAndPassword(String email, String encodedPw);

    User findByEmail(String email);
}
