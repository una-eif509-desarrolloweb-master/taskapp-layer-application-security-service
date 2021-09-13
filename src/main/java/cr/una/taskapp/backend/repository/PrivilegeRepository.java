package cr.una.taskapp.backend.repository;

import cr.una.taskapp.backend.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for Privilege Repository
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 7/29/21
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
