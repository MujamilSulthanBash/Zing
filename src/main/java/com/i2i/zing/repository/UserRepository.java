package com.i2i.zing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.i2i.zing.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * <p>
     *     This method is responsible for check the given name is present or not.
     * </p>
     * @param name - name of the user.
     * @return boolean true if the user is already present else return false.
     */
    boolean existsByUserNameAndIsDeletedFalse(String name);

    /**
     * <p>
     *     This method is responsible for check the email is there or not
     * </p>
     * @param email - email of the user.
     * @return boolean true if its present else return false.
     */
    boolean existsByEmailIdIgnoreCase(String email);

    /**
     * <p>
     *     This method is responsible for get the user by email
     * </p>
     * @param email - email of the user.
     * @return {@link User} details.
     */
    User findByEmailIdIgnoreCase(String email);

    /**
     * <p>
     *     This method is responsible for get the users by their location
     * </p>
     * @param location - Location of the User.
     * @return - List of {@link User} details.
     */
    @Query(value = "FROM User u RIGHT JOIN FETCH DeliveryPerson d ON d.user.id = u.id WHERE u.location = :location")
    List<User> findUsersByLocation(String location);
}
