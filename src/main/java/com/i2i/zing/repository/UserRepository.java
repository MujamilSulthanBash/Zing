package com.i2i.zing.repository;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUserName(String name);

    boolean existsByEmailIdIgnoreCase(String email);

    User findByEmailIdIgnoreCase(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.location = :location AND r.name = :userRole")
    List<User> findUsersByLocation(String location, String userRole);
}
