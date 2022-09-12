package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.Donation;
import kbsc.greenFunding.entity.DonationOrder;
import kbsc.greenFunding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DonationOrderJpaRepository extends JpaRepository<DonationOrder, Long> {

    Long countAllByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    Long countAllByDonation(Donation donation);

    List<DonationOrder> findAllByUser(User user);
}
