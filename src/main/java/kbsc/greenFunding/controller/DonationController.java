package kbsc.greenFunding.controller;


import kbsc.greenFunding.dto.project.DonationRewardReq;
import kbsc.greenFunding.dto.project.ProjectDonationInfoRes;
import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;

    @GetMapping("/{id}")
    public ApiResponse<ProjectDonationInfoRes> getProjectDonationInfo(@PathVariable("id")Long projectId) {
        return ApiResponse.success(ApiCode.SUCCESS, donationService.getProjectDonationInfo(projectId));
    }

}
