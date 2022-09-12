package kbsc.greenFunding.dto.user;

import kbsc.greenFunding.entity.MaterialCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageProjectRes {
    private Long projectId;
    private String thumbnail;
    private String title;
    private String summary;
    private MaterialCategory category;
}
