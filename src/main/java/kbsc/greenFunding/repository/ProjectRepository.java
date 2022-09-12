package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.MaterialCategory;
import kbsc.greenFunding.entity.Project;
import kbsc.greenFunding.entity.ProjectType;
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
                        " left join fetch p.upcyclingList ul" +
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

    public List<Project> findAllWithDonationByType(ProjectType type) {
        return em.createQuery("select distinct p from Project  p" +
                " join fetch p.donation d where p.projectType=:type", Project.class)
                .setParameter("type", type)
                .getResultList();
    }

    public List<Project> findALlWithDonationByCategory(MaterialCategory category) {
        return em.createQuery("select distinct p from Project  p" +
                        " join fetch p.donation d where p.category=:category", Project.class)
                .setParameter("category", category)
                .getResultList();
    }

    public List<Project> findAllWithDonationByTypeAndCategory(ProjectType type, MaterialCategory category) {
        return em.createQuery("select distinct p from Project  p" +
                        " join fetch p.donation d" +
                        " where p.projectType=:type  and p.category=:category", Project.class)
                .setParameter("type", type)
                .setParameter("category", category)
                .getResultList();
    }
}
