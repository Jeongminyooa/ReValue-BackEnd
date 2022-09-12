package kbsc.greenFunding.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
public class MyPageRes {
    // ID, 활동 일수,  [참여한 기부 켐페인], [참여한 펀딩 프로젝트]
    private String userId;
    private Long activeDays;
    private List<MyPageProjectRes> donationList;
    private List<MyPageProjectRes> rewardList;
}
