package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.DonationOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationOrderRepository extends JpaRepository<DonationOrder, Long> {
}
