package cr.una.taskapp.backend.mapper;

import cr.una.taskapp.backend.dto.RoleResult;
import cr.una.taskapp.backend.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Interface for Role Mapper
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 8/2/21
 */
@Mapper
public interface RoleMapper {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    Role roleFromRoleResult(RoleResult roleResult);
    RoleResult roleResultFromRole(Role role);

    List<RoleResult> roleResultListFromRoleList(List<Role> roleList);
    List<Role> roleListFromRoleResultList(List<RoleResult> roleResultList);
}
