package kbsc.greenFunding.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpcyclingOrder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upcycling_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "upcyclingOrder", cascade = CascadeType.ALL)
    private List<UpcyclingOrderItem> orderItemList = new ArrayList<>();

    private LocalDateTime orderDate;

    //== 연관관계 메서드==//
    public void addOrderItemList(UpcyclingOrderItem upcyclingOrderItem) {
        orderItemList.add(upcyclingOrderItem);
        upcyclingOrderItem.setUpcyclingOrder(this);
    }
}
