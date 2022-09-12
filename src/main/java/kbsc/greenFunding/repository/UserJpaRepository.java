package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
