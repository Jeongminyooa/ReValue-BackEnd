package kbsc.greenFunding.dto.main;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpcyclingReq {
    private Long upcyclingId; // 리워드ID
    private int count; // 수량
}
