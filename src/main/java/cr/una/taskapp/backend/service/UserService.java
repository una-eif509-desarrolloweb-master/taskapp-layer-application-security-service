package cr.una.taskapp.backend.service;

import cr.una.taskapp.backend.dto.UserInput;
import cr.una.taskapp.backend.dto.UserResult;
import cr.una.taskapp.backend.dto.UserSignUpInput;
import javassist.NotFoundException;

import java.util.List;

/**
 * Interface definition for User Service
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 9/12/21
 */
public interface UserService {

    /**
     * Find all entities
     * @return a list of Users
     */
    List<UserResult> findAll();

    /**
     * Get one user by id
     * @param id
     * @return the User found
     */
    UserResult findById(Long id) throws NotFoundException;

    /**
     * Find a user by the email provided
     * @param email the email of the user
     * @return the User found
     * @throws NotFoundException
     */
    UserResult findByEmail(String email);

    /**
     * Save and flush a user entity in the database
     * @param userInput
     * @return the user created
     */
    UserResult create(UserInput userInput);

    /**
     * Signup a new user in the database
     * @param userSignUpInput
     * @return the user created
     */
    UserResult signUp(UserSignUpInput userSignUpInput);

    /**
     * Update a user entity in the database
     * @param userInput
     * @return
     */
    UserResult update(UserInput userInput) throws NotFoundException;

    /**
     * Delete a user by id from Database
     * @param id
     */
    void deleteById(Long id) throws NotFoundException;
}
