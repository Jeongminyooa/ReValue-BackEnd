package kbsc.greenFunding.entity;

import lombok.Builder;
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

    private int totalWeight; // 목표 무게

    private int minWeight; // 일인당 최소 무게

    private int remainingWeight; // 잔여 무게

    @Enumerated(EnumType.STRING)
    private DonationMethod method;

    private String address;

    private String description;

    @Builder(builderClassName = "donaionBuilder", builderMethodName="donaionBuilder")
    public Donation(int totalWeight) {
        this.totalWeight = totalWeight;
    }
}
