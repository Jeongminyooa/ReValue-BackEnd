package kbsc.greenFunding.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class DonationOrderRepository {

    private final EntityManager em;

    public Long countOrdersToday(LocalDateTime startDate, LocalDateTime endDate) {
        return em.createQuery("select COUNT(d.id) from DonationOrder d " +
                "where :startDate <= d.orderDate and d.orderDate <= :endDate", Long.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
    }
}
