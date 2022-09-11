package kbsc.greenFunding.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpcyclingRewardRes {
    private Long projectId;
    private int amount; // 프로젝트 목표 금액
    private List<UpcyclingReward> upcyclingList;

    @Data
    @AllArgsConstructor
    public static class UpcyclingReward {
        private Long upcyclingId;
        private String title;
        private int price;
        private String description;
        private int totalCount;
    }
}
