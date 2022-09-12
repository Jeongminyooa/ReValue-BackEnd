package kbsc.greenFunding.dto.main;

import kbsc.greenFunding.entity.MaterialCategory;
import kbsc.greenFunding.entity.ProjectType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ProjectDetailRes {
    /**
     * ID, 대표 이미지, 유형, 제목, 시작일, 종료일, 카테고리, 판매자 ID,
     * 리워드 목표 금액, 리워드 *잔여 수량*, 기부 목표 무게, 기부 잔여 무게, 기부 방법, 기부 주소
     * 이미지 리스트, 프로젝트 소개,
     *
     * 리워드 리스트[리워드ID, 이름, 설명, 가격, 제한 수량, *잔여 수량*]
     * 기부 리스트[기부ID, 기부 최소 단위, 기부 방법]
     */
    private Long projectId;
    private String thumbnail;
    private ProjectType projectType;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private MaterialCategory category;
    private String user;
    private int rewardAmount; // 리워드 목표 금액
    private int rewardRemainingCount; // 리워드 잔여 수량
    private int rewardTotalCount; // 리워드 총 수
    private int donationTotalWeight; // 기부 목표 무게
    private int donationRemainingWeight; // 기부 잔여 무게
    private Long donationParticipants; // 기부 참여자 수
    private List<String> imgList;
    private String content;

    private List<UpcyclingDetailRes> rewardDetail;
    private DonationDetailRes donationDetail;
}
