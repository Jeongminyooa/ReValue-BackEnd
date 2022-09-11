package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {
}
