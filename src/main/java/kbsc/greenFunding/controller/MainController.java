package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.main.DonationReq;
import kbsc.greenFunding.dto.main.MainListRes;
import kbsc.greenFunding.dto.main.ProjectDetailRes;
import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.entity.DonationMethod;
import kbsc.greenFunding.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public ApiResponse<MainListRes> getMainList() {
        MainListRes mainList = mainService.getMainList();

        return ApiResponse.success(ApiCode.SUCCESS, mainList);
    }

    @GetMapping("/{projectId}")
    public ApiResponse<ProjectDetailRes> getProjectDetail(@PathVariable("projectId") Long projectId) {
        ProjectDetailRes projectDetail = mainService.getProjectDetail(projectId);
        return ApiResponse.success(ApiCode.SUCCESS, projectDetail);
    }

    @PostMapping("/donation")
    public ApiResponse<Long> saveDonation(@RequestBody DonationReq donationReq) {

        Long orderId = mainService.saveDonation(donationReq);
        return ApiResponse.success(ApiCode.SUCCESS, orderId);
    }

}
