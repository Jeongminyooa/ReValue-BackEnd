package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final EntityManager em;

    public Project getProjectDetail(Long projectId) {
        return em.createQuery("select distinct p from Project p" +
                        " join fetch p.upcyclingList ul" +
                        " join fetch p.user u" +
                        " left join fetch p.donation d" +
                        " where p.id = :projectId", Project.class)
                .setParameter("projectId", projectId)
                .getSingleResult();

    }

    public List<Project> findAllWithDonation() {
        return em.createQuery("select distinct p from Project p" +
                " join fetch p.donation d", Project.class)
                .getResultList();
    }
}
