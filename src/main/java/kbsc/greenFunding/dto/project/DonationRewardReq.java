package kbsc.greenFunding.dto.project;

import lombok.Getter;

@Getter
public class DonationRewardReq {
    private String description;
    private int minWeight;
    private String donationMethod;
    private String address;
}
