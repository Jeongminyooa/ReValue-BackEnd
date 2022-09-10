package kbsc.greenFunding.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpcyclingRewardReq {
    private String title;
    private int price;
    private String description;
    private int totalCount;
}
