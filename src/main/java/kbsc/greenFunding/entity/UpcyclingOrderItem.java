package kbsc.greenFunding.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class UpcyclingOrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upcycling_order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upcycling_id")
    private Upcycling upcycling;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upcycling_order_id")
    private UpcyclingOrder upcyclingOrder;

    private int count;

    //== 연관관계 메서드 ==//
    public void setUpcyclingOrder(UpcyclingOrder upcyclingOrder) {
        this.upcyclingOrder = upcyclingOrder;
        upcyclingOrder.getOrderItemList().add(this);
    }
}
