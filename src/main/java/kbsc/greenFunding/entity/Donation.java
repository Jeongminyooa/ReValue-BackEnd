package kbsc.greenFunding.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Donation {

    // 총 목표 무게, 1인당 최소 무게, 기부 방법, 기부받을 주소, 설명

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Long id;

    private int totalWeight;

    private int minWeight;

    @Enumerated(EnumType.STRING)
    private DonationMethod method;

    private String address;

    private String description;
}