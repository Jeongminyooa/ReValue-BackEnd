package kbsc.greenFunding.dto.main;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpcyclingDetailRes {
    // 리워드 리스트[리워드ID, 이름, 설명, 가격, 제한 수량, *잔여 수량*]
    private Long rewardId;
    private String title;
    private String description;
    private int price;
    private int totalCount;
    private int remainingCount;
}
