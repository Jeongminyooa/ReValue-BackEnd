package kbsc.greenFunding.repository;

import kbsc.greenFunding.entity.UpcyclingOrder;
import kbsc.greenFunding.entity.UpcyclingOrderItem;
import kbsc.greenFunding.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UpcyclingOrderRepository {

    private final EntityManager em;

    public List<UpcyclingOrder> getOrderWithItemByUser(User user) {
        return em.createQuery("select distinct o from UpcyclingOrder o" +
                " left join fetch o.orderItemList il" +
                " left join fetch il.upcycling up" +
                " left join fetch up.project p" +
                " where o.user=:user", UpcyclingOrder.class)
                .setParameter("user", user)
                .getResultList();
    }
}
