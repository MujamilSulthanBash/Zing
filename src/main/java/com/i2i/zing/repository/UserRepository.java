package com.i2i.zing.repository;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUserName(String name);
}
