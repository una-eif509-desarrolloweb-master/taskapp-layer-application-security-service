package cr.una.taskapp.backend.mapper;

import cr.una.taskapp.backend.dto.UserInput;
import cr.una.taskapp.backend.dto.UserResult;
import cr.una.taskapp.backend.dto.UserSignUpInput;
import cr.una.taskapp.backend.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Interface for User Mapper
 *
 * @author Maikol Guzman <maikol@guzmanalan.com>
 * @create 9/12/21
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {RoleMapper.class})
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    List<UserResult> userResultListFromUserList(List<User> userList);

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "password", ignore = true)
    User userFromUserResult(UserResult userResult);

    @Mapping(target = "tokenExpired", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User userFromUserInput(UserInput userInput);

    UserResult userResultFromUser(User user);

    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "tokenExpired", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User userFromUserSignUpInput(UserSignUpInput userSignUpInput);

    @Mapping(target = "tokenExpired", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void userFromUserInput(UserInput dto, @MappingTarget User entity);
}
