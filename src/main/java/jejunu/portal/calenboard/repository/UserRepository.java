package jejunu.portal.calenboard.repository;

import jejunu.portal.calenboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, User> {
}
