package kbsc.greenFunding.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OwnerProjectDetailRes {
    private Long projectId;
    private String title; // 제목
    private String summary; // 요약
    private String type; // 타입
    private LocalDateTime startDate; // 시작 날짜
    private LocalDateTime endDate; // 마감 날짜
    private long period; // 남은 기간
    private String category; // 소재 카테고리
    private String thumbnail; // 대표 이미지
    private String content; // 상세 설명
    private List<String> imageList; // 프로젝트 소개 이미지

    private DonationDetailRes donationDetailRes;
    private UpcyclingDetailRes upcyclingDetailRes;

    // 기부 프로젝트
    @AllArgsConstructor
    @Data
    public static class DonationDetailRes {
        private int donationRate; // 기부 달성량
        private int totalWeight; // 목표 기부량
        private int minWeight; // 최소 기부량
        private int fundingWeight; // 모인 소재 용량
        private Long totalSupporter; // 총 서포터 수
        private String description; // 소재 설명
        private String donationMethod; // 전달 방법
        private String address; // 전달 주소
    }

    // 업사이클링 프로젝트
    @AllArgsConstructor
    @Data
    public static class UpcyclingDetailRes {
        private int upcyclingRate; // 업사이클링 리워드 달성량
        private int amount; // 목표 금액
        private int fundingAmount; // 총 펀딩 금액
        private Long totalSupporter; // 총 서포터 수
        private List<RewardDetailRes> rewardList;

    }

    // 판매 리워드
    @AllArgsConstructor
    @Data
    public static class RewardDetailRes {
        private String title; //제목
        private String description;
        private int price; // 가격
        private int totalCount; // 제한 수량
    }

}
