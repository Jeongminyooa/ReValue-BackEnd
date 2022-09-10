package kbsc.greenFunding.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DonationRewardRes {
    private Long projectId;
    private Long donationId;
}
