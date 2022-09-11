package kbsc.greenFunding.dto.main;

import kbsc.greenFunding.entity.DonationMethod;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonationReq {
    private Long donationId;
    private int weight;
    private DonationMethod method;
}
