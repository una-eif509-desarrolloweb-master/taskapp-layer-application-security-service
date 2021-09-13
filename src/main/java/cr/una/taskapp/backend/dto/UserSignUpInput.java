package cr.una.taskapp.backend.dto;

import lombok.Data;

/**
 * DTO input for the entity User Sign Up
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 8/3/21
 */
@Data
public class UserSignUpInput {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
