package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.Donation;
import kbsc.greenFunding.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonationJpaRepository extends JpaRepository<Donation, Long> {
}
