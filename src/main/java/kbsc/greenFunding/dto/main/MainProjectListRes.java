package kbsc.greenFunding.dto.main;

import kbsc.greenFunding.entity.MaterialCategory;
import kbsc.greenFunding.entity.ProjectType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MainProjectListRes {
    /**
     *  카테고리별 프로젝트 [id, 유형, 대표 이미지, 제목, 카테고리, 달성율, 진행기간] 리스트
     */

    private Long projectId;
    private MaterialCategory category;
    private ProjectType projectType; // 기부만, 리워드만, 모두
    private String thumbnail;
    private String title;
    private String content;
    private int donationRate;
    private int rewardRate;
    // private LocalDate startDate;
    // private LocalDate endDate;
    private Long remainingDate;
}
