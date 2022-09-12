package kbsc.greenFunding.dto.project;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ProjectInfoReq {
    private String title;
    private String summary;
}
