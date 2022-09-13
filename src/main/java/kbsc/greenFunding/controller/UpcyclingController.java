package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.project.UpcyclingRewardReq;
import kbsc.greenFunding.dto.project.UpcyclingRewardRes;
import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.service.UpcyclingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/upcyclings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 컨트롤러에서 설정
public class UpcyclingController {
    private final UpcyclingService upcyclingService;

    @GetMapping("/{id}")
    public ApiResponse<UpcyclingRewardRes> getUpcyclingReward(@PathVariable("id") Long projectId) {
        return ApiResponse.success(ApiCode.SUCCESS, upcyclingService.getUpcyclingRewardList(projectId));
    }

    @PostMapping("/{id}")
    public ApiResponse<UpcyclingRewardRes> saveUpcyclingReward(@PathVariable("id") Long projectId,
                                                               @RequestBody UpcyclingRewardReq upcyclingRewardReq) {
        projectId = upcyclingService.postUpcyclingReward(projectId, upcyclingRewardReq);
        return ApiResponse.success(ApiCode.CREATED, upcyclingService.getUpcyclingRewardList(projectId));
    }

}
