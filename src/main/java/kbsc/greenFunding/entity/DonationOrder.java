package kbsc.greenFunding.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class DonationOrder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_order_id")
    private Long id;

    private int count; //수량

    private LocalDateTime orderDate; // 신청 날짜

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
