package cr.una.taskapp.backend.service;

import cr.una.taskapp.backend.dto.UserInput;
import cr.una.taskapp.backend.dto.UserResult;
import cr.una.taskapp.backend.dto.UserSignUpInput;
import cr.una.taskapp.backend.mapper.UserMapper;
import cr.una.taskapp.backend.model.User;
import cr.una.taskapp.backend.repository.RoleRepository;
import cr.una.taskapp.backend.repository.UserRepository;
import javassist.NotFoundException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementation User Service
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 9/12/21
 */
@Service
public class AbstractUserService implements UserService  {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Find all entities
     *
     * @return a list of Users
     */
    @Override
    public List<UserResult> findAll() {
        return UserMapper.MAPPER.userResultListFromUserList(userRepository.findAll());
    }

    /**
     * Get one user by id
     *
     * @param id
     * @return the User found
     */
    @Override
    public UserResult findById(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("The user with the id: %s not found!", id));
        }
        return UserMapper.MAPPER.userResultFromUser(user.get());
    }

    /**
     * Find a user by the email provided
     *
     * @param email the email of the user
     * @return the User found
     * @throws NotFoundException
     */
    @Override
    public UserResult findByEmail(String email) {
        Optional<User> user;
        try {
            user = userRepository.findByEmail(email).stream().findFirst();
        } catch (NoResultException exception) {
            user = Optional.empty();
        }
        return UserMapper.MAPPER.userResultFromUser(user.get());
    }

    /**
     * Save and flush a user entity in the database
     *
     * @param userInput
     * @return the user created
     */
    @Override
    public UserResult create(UserInput userInput) {
        User user = UserMapper.MAPPER.userFromUserInput(userInput);
        return UserMapper.MAPPER.userResultFromUser(userRepository.saveAndFlush(normalizeUser(user, true)));
    }

    /**
     * Signup a new user in the database
     *
     * @param userSignUpInput
     * @return the user created
     */
    @Override
    public UserResult signUp(UserSignUpInput userSignUpInput) {
        User user = UserMapper.MAPPER.userFromUserSignUpInput(userSignUpInput);
        return UserMapper.MAPPER.userResultFromUser(userRepository.saveAndFlush(normalizeUser(user, true)));
    }

    /**
     * Update a user entity in the database
     *
     * @param userInput
     * @return
     */
    @Override
    public UserResult update(UserInput userInput) throws NotFoundException {
        Optional <User> user = userRepository.findById(userInput.getIdUser());
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("The user with the id: %s not found!", userInput.getIdUser()));
        }
        User userUpdated = user.get();
        UserMapper.MAPPER.userFromUserInput(userInput, userUpdated);
        return UserMapper.MAPPER.userResultFromUser(userRepository.save(userUpdated));
    }

    /**
     * Delete a user by id from Database
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) throws NotFoundException {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException(String.format("The user with the id: %s not found!", id));
        } else {
            userRepository.deleteById(id);
        }
    }

    /**
     * Normalize the User Object
     *
     * @param user      the model of user
     * @param isNewUser verify is the user is new
     * @return normalized user model
     */
    private User normalizeUser(User user, boolean isNewUser) {
        if (isNewUser) {
            DateTime dt = new DateTime();
            user.setDateCreated(dt.toDate());
            user.setEnabled(true);
        }

        if (user.getRoles() == null || user.getRoles().size() == 0) {
            user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return user;
    }
}
