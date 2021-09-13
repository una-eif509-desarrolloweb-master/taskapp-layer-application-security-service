package cr.una.taskapp.backend.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO input for the entity User
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 7/29/21
 */
@Data
public class UserInput {
    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean enabled;
    private List<RoleResult> roles;
}
