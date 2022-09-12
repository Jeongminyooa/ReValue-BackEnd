package kbsc.greenFunding.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationOrder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_order_id")
    private Long id;

    private int weight; //무게

    private LocalDateTime orderDate; // 신청 날짜

    private DonationMethod method; // 기부 방법

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
