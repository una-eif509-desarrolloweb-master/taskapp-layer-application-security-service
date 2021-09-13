package cr.una.taskapp.backend.webservice;

import cr.una.taskapp.backend.dto.UserInput;
import cr.una.taskapp.backend.dto.UserResult;
import cr.una.taskapp.backend.dto.UserSignUpInput;
import cr.una.taskapp.backend.service.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Webservice Controller for User
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 7/25/21
 */
@RestController
@RequestMapping("${url.user}")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Webservice to find all Users
     * @return A list of users
     */
    @GetMapping()
    @ResponseBody
    public List<UserResult> findAll() {
        return userService.findAll();
    }

    /**
     * Webservice to find one User by the id
      * @param id
     * @return the User found
     */
    @GetMapping("{id}")
    @ResponseBody
    public UserResult findById(@PathVariable Long id) throws NotFoundException {
        return userService.findById(id);
    }

    /**
     * Webservice to save a new user and flush into database
     * @param userInput
     * @return userResult Updated
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResult createUser(@RequestBody UserInput userInput) {
        return userService.create(userInput);
    }

    /**
     * Sing-up and create a new user
     * @param userSignUpInput
     * @return the User Result
     */
    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResult signUp(@RequestBody UserSignUpInput userSignUpInput) {
        return userService.signUp(userSignUpInput);
    }

    /**
     * Update the existing User
     * @param userInput the user saved
     * @return the user updated
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResult update(@RequestBody UserInput userInput) throws NotFoundException {
        return userService.update(userInput);
    }

    /**
     * Delete user by id
     * @param id the id of the entity
     */
    @DeleteMapping("{id}")
    @ResponseBody
    public void deleteById(@PathVariable Long id) throws NotFoundException {
            userService.deleteById(id);
    }
}
