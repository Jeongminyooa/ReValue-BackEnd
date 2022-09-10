package kbsc.greenFunding.dto.main;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MainListRes {
    /**
     *  일간 기부/상품 리워드 참여자 수, 카테고리별 프로젝트 [id, 유형, 대표 이미지, 제목, 카테고리, 달성율, 진행기간] 리스트
     *     - count(donation_order 의 신청 날짜 == 오늘) + count(upcycling_order 의 신청 날짜 == 오늘)
     */
    private Long totalCount;
    private List<MainProjectListRes> projectList;
}
