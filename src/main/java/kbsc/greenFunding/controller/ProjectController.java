package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.project.ProjectInfoReq;
import kbsc.greenFunding.dto.project.ProjectPlanReq;
import kbsc.greenFunding.dto.project.ProjectTypeReq;
import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.dto.response.ErrorCode;
import kbsc.greenFunding.entity.Project;
import kbsc.greenFunding.exception.NoEnumException;
import kbsc.greenFunding.service.AwsS3Service;
import kbsc.greenFunding.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

        projectId = projectService.postProjectInfo(projectInfoReq, imageUrl, projectId);

        return ApiResponse.success(ApiCode.SUCCESS, projectId);
    }

    @PostMapping("/{id}/saved-plan")
    public ApiResponse<Long> saveProjectPlan(@PathVariable("id") Long projectId,
                                             @RequestBody ProjectPlanReq projectPlanReq) {
        projectId = projectService.postProjectPlan(projectPlanReq, projectId);

        return ApiResponse.success(ApiCode.SUCCESS, projectId);
    }

    @PostMapping("/{id}/saved-content")
    public ApiResponse<Long> saveProjectDescription(@PathVariable("id") Long projectId,
                                                       @RequestPart("content") String content,
                                                       @RequestPart("fileList") List<MultipartFile> fileList) {

        // 이미지 예외처리
        if(fileList.size() > 10) {
            throw new NoEnumException("max upload file number exceeded", ErrorCode.MAX_NUMBER_FILE_EXCEED);
        }
        List<String> fileUrlList = awsS3Service.uploadImage(fileList);

        projectId = projectService.postProjectContent(projectId, content, fileUrlList);

        return ApiResponse.success(ApiCode.SUCCESS, projectId);
    }

}
