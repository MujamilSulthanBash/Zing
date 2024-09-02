package com.i2i.zing.service;

import com.i2i.zing.model.User;

/**
 * <p>
 *     This interface has abstract methods for
 *     User Operations
 * </p>
 */
public interface UserService {
    /**
     * <p>
     *     This method create Admin account
     * </p>
     */
    void createAdmin();

    /**
     * <p>
     *     This method return the user by Email ID
     * </p>
     * @param emailId - Email id of the User
     * @return - User as Entity Object
     */
    User retrieveByEmail(String emailId);

    /**
     * <p>
     *     This method return the Boolean if user email
     *     already Exist or not
     * </p>
     * @param emailId - Email id of the User
     * @return - boolean value true or false if email exist of not
     */
    boolean checkByEmailId(String emailId);

    /**
     * <p>
     *     This method create a User in the Database
     * </p>
     * @param user - User as Entity Object
     * @return - User as Entity Object
     */
    User createUser(User user);

}
