package cr.una.taskapp.backend.service;

import cr.una.taskapp.backend.dto.RoleResult;
import cr.una.taskapp.backend.mapper.RoleMapper;
import cr.una.taskapp.backend.model.Privilege;
import cr.una.taskapp.backend.model.Role;
import cr.una.taskapp.backend.repository.RoleRepository;
import cr.una.taskapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cr.una.taskapp.backend.common.Constants.EMPTY;

import java.util.*;

/**
 * Custom service of User Detail Service from Spring
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 9/12/21
 */
@Service
@Transactional
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userAuth = null;
        Optional<cr.una.taskapp.backend.model.User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            return new User(
                    EMPTY, EMPTY, true, true, true, true,
                    getAuthorities(Arrays.asList(
                            roleRepository.findByName("ROLE_USER"))));
        }

        String name = user.get().getFirstName().concat(EMPTY).concat(user.get().getLastName());
        List<RoleResult> roleResults = RoleMapper.MAPPER.roleResultListFromRoleList((List<Role>) user.get().getRoles());

        userAuth = new User(
                user.get().getEmail(), user.get().getPassword(), user.get().isEnabled(), true, true,
                true, getAuthorities(user.get().getRoles()));

        return userAuth;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
