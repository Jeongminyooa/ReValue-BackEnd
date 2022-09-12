package kbsc.greenFunding.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OwnerProjectListRes {
    private Long projectId;
    private String title; // 제목
    private String type; // 타입
    private LocalDateTime startDate; // 시작 날짜
    private LocalDateTime endDate; // 마감 날짜
    private String category; // 소재 카테고리
    private String thumbnail; // 대표 이미지
    private DonationDetailRes donationDetailRes;
    private UpcyclingDetailRes upcyclingDetailRes;

    @AllArgsConstructor
    @Data
    public static class DonationDetailRes {
        private int totalWeight; // 목표 무게
        private int minWeight; // 최소 기부 무게
        private int donationRate; // 기부 달성률
        private int fundingWeight; // 현재 달성 무게
    }

    @AllArgsConstructor
    @Data
    public static class UpcyclingDetailRes {
        private int amount; // 목표 금액
        private int rewardRate; // 리워드 달성률
        private int fundingAmount; // 현재 달성 금액
    }
}
