package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.project.ProjectInfoReq;
import kbsc.greenFunding.dto.project.ProjectTypeReq;
import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.service.AwsS3Service;
import kbsc.greenFunding.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final AwsS3Service awsS3Service;

    @PostMapping("/saved-type")
    public ApiResponse<Long> saveProjectType(@RequestBody ProjectTypeReq projectTypeReq) {
        // jwt - userId

        Long userId = Long.valueOf(1);

        Long projectId = projectService.postProjectType(userId, projectTypeReq);

        return ApiResponse.success(ApiCode.SUCCESS, projectId);
    }

    @PostMapping("/{id}/saved-info")
    public ApiResponse<Long> saveProjectInfo(@PathVariable("id") Long projectId,
                                            @RequestPart("project-info-req") ProjectInfoReq projectInfoReq,
                                             @RequestPart("thumbnail") MultipartFile imageFile) {

        String imageUrl = awsS3Service.uploadImage(imageFile);

        projectService.postProjectInfo(projectInfoReq, imageUrl, projectId);

        return ApiResponse.success(ApiCode.SUCCESS, projectId);
    }

}
