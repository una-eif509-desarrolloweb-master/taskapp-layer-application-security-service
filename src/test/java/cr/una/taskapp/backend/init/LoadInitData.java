package cr.una.taskapp.backend.init;

import cr.una.taskapp.backend.dto.PriorityDetails;
import cr.una.taskapp.backend.model.Priority;
import cr.una.taskapp.backend.model.Privilege;
import cr.una.taskapp.backend.model.Role;
import cr.una.taskapp.backend.model.User;
import cr.una.taskapp.backend.repository.PriorityRepository;
import cr.una.taskapp.backend.repository.PrivilegeRepository;
import cr.una.taskapp.backend.repository.RoleRepository;
import cr.una.taskapp.backend.repository.UserRepository;
import cr.una.taskapp.backend.service.PriorityService;
import cr.una.taskapp.backend.service.UserService;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Description
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 9/12/21
 */
@SpringBootTest
public class LoadInitData {
    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void defaultUserData() {
        DateTime dt = new DateTime();

        Privilege privilegeRead = new Privilege();
        privilegeRead.setName("READ_PRIVILEGE");
        privilegeRepository.saveAndFlush(privilegeRead);

        Privilege privilegeWrite = new Privilege();
        privilegeWrite.setName("WRITE_PRIVILEGE");
        privilegeRepository.saveAndFlush(privilegeWrite);

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleAdmin.setPrivileges(Arrays.asList(privilegeWrite, privilegeRead));
        roleRepository.saveAndFlush(roleAdmin);

        Role roleStaff = new Role();
        roleStaff.setName("ROLE_STAFF");
        roleStaff.setPrivileges(Arrays.asList(privilegeRead));
        roleRepository.saveAndFlush(roleStaff);

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        roleUser.setPrivileges(Arrays.asList(privilegeRead));
        roleRepository.saveAndFlush(roleUser);

        User user;

        user = new User();
        user.setFirstName("Maikol");
        user.setLastName("Guzman");
        user.setEmail("mike@guzmanalan.com");
        user.setPassword("$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK"); // Encrypted pass: 12345
        user.setDateCreated(dt.toDate());
        user.setEnabled(true);
        user.setRoles(Arrays.asList(roleAdmin));
        userRepository.saveAndFlush(user);

        assertEquals(2, privilegeRepository.findAll().size());
        assertEquals(3, roleRepository.findAll().size());
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    void loadPriorityData() {
        Priority priority1 = new Priority();
        Priority priority2 = new Priority();
        Priority priority3 = new Priority();

        priority1.setLabel("High");
        priority2.setLabel("Medium");
        priority3.setLabel("Low");

        priorityRepository.saveAndFlush(priority1);
        priorityRepository.saveAndFlush(priority2);
        priorityRepository.saveAndFlush(priority3);

        assertEquals(3, priorityRepository.findAll().size());
    }
}
