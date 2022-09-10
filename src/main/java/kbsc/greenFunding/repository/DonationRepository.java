package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
