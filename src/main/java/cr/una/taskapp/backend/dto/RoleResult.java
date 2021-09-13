package cr.una.taskapp.backend.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO for the entity Role
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 7/29/21
 */
@Data
public class RoleResult {
    private Long id;
    private String name;
    private List<PrivilegeResult> privileges;
}
