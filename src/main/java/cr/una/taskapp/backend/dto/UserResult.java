package cr.una.taskapp.backend.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * DTO result for the entity User
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 7/25/21
 */
@Data
public class UserResult {
    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private boolean tokenExpired;
    private Date dateCreated;
    private List<RoleResult> roles;
}
