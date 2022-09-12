package kbsc.greenFunding.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UpcyclingOrderItemRepository {

    private final EntityManager em;

    public List<Long> countByProjectGroupByUser(Long projectId) {
        return em.createQuery("select count(o.user) from UpcyclingOrderItem i" +
                " join i.upcycling u" +
                " join i.upcyclingOrder o" +
                " where u.project.id = :projectId" +
                        " group by o.user", Long.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }
}
