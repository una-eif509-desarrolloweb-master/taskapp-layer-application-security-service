package cr.una.taskapp.backend.repository;

import cr.una.taskapp.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface for User Repository
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 9/12/21
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by the email
     * @param email the email of the user
     * @return a User
     */
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email=:email")
    public Optional<User> findByEmail(@Param("email") String email);
}
