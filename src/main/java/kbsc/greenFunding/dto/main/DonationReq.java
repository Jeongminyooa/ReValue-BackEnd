package kbsc.greenFunding.dto.main;

import kbsc.greenFunding.entity.DonationMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationReq {
    private Long donationId;
    private int weight;
    private DonationMethod method;
}
