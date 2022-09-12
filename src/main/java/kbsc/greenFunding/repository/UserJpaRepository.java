package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

=======
public interface UserJpaRepository extends JpaRepository<User, Long> {
>>>>>>> main
}
