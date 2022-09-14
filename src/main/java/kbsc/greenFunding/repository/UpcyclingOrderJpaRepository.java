package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.Project;
import kbsc.greenFunding.entity.Upcycling;
import kbsc.greenFunding.entity.UpcyclingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface UpcyclingOrderJpaRepository extends JpaRepository<UpcyclingOrder, Long> {

    Long countAllByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
