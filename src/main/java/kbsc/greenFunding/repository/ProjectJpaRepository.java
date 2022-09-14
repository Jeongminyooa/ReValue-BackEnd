package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.Project;
import kbsc.greenFunding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(User user);
}
