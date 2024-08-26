package com.i2i.zing.repository;

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

    @Query(value = "SELECT * FROM user u LEFT JOIN deliveryperson d ON u.userid = d.userid WHERE u.location = :location", nativeQuery = true)
    List<User> findUsersByLocation(String location);
}
