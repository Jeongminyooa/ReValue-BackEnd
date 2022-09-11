package kbsc.greenFunding.dto.main;

import kbsc.greenFunding.entity.DonationMethod;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonationDetailRes {
    /**
     * 리워드 리스트[리워드ID, 이름, 설명, 가격, 제한 수량, *잔여 수량*]
     * 기부 리스트[기부ID, 기부 최소 단위, 기부 방법]
     */

    private Long donationId;
    private int minWeight;
    private DonationMethod method;
}
