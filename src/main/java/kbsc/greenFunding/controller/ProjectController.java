package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.project.ProjectTypeDto;
import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/saved-type")
    public ApiResponse<Long> saveProjectType(@RequestBody ProjectTypeDto dto) {
        // jwt - userId

        Long userId = Long.valueOf(1);

        Long projectId = projectService.postProjectType(userId, dto.getProjectType(), dto.getCategory());

        return ApiResponse.success(ApiCode.SUCCESS, projectId);
    }

}
