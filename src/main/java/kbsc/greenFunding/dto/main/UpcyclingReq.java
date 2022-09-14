package kbsc.greenFunding.dto.main;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpcyclingReq {
    private Long upcyclingId; // 리워드ID
    private int count; // 수량
}
