package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.owner.OwnerProjectDetailRes;
import kbsc.greenFunding.dto.owner.OwnerProjectListRes;
import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 컨트롤러에서 설정
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping
    public ApiResponse<List<OwnerProjectListRes>> getOwnerProjectList() {
        // jwt - userId

        Long userId = Long.valueOf(1);

        List<OwnerProjectListRes> ownerProjectListResList = ownerService.getOwnerProjectList(userId);

        return ApiResponse.success(ApiCode.SUCCESS, ownerProjectListResList);
    }

    @GetMapping("/{id}")
    public ApiResponse<OwnerProjectDetailRes> getOwnerProjectDetail(@PathVariable("id")Long projectId) {
        OwnerProjectDetailRes ownerProjectDetailRes = ownerService.getOwnerProject(projectId);

        return ApiResponse.success(ApiCode.SUCCESS, ownerProjectDetailRes);
    }
}
