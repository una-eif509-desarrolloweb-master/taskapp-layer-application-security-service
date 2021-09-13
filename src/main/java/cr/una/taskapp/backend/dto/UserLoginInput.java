package cr.una.taskapp.backend.dto;

import lombok.Data;

/**
 * DTO input for the entity Login User
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 8/3/21
 */
@Data
public class UserLoginInput {
    private String username;
    private String password;
}
