package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepository extends JpaRepository<Project, Long> {
}
