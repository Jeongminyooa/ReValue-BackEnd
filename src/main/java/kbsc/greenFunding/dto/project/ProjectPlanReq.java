package kbsc.greenFunding.dto.project;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjectPlanReq {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int totalWeight;
    private int amount;
}
