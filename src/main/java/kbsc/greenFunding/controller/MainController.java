package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.main.DonationReq;
import kbsc.greenFunding.dto.main.MainListRes;
import kbsc.greenFunding.dto.main.ProjectDetailRes;
import kbsc.greenFunding.dto.main.UpcyclingReq;
import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.entity.DonationMethod;
import kbsc.greenFunding.entity.MaterialCategory;
import kbsc.greenFunding.entity.ProjectType;
import kbsc.greenFunding.security.jwt.JwtTokenProvider;
import kbsc.greenFunding.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
@CrossOrigin(origins = "http://localhost:3000") // 컨트롤러에서 설정
public class MainController {

    private final MainService mainService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("")
    public ApiResponse<MainListRes> getMainList(@RequestParam("projectType") String type,
                                                @RequestParam("category")String category) {
        ProjectType projectType = ProjectType.valueOf(type);
        MaterialCategory materialCategory = MaterialCategory.valueOf(category);

        MainListRes mainList = mainService.getMainList(projectType, materialCategory);

        return ApiResponse.success(ApiCode.SUCCESS, mainList);
    }

    @GetMapping("/{projectId}")
    public ApiResponse<ProjectDetailRes> getProjectDetail(@PathVariable("projectId") Long projectId) {
        ProjectDetailRes projectDetail = mainService.getProjectDetail(projectId);
        return ApiResponse.success(ApiCode.SUCCESS, projectDetail);
    }

    @PostMapping("/donation")
    public ApiResponse<Long> saveDonation(@RequestBody DonationReq donationReq,
                                          HttpServletRequest req) {
        String token = jwtTokenProvider.getJwtFromRequest(req);
        Long userId = jwtTokenProvider.getUserPk(token);

        Long orderId = mainService.saveDonation(donationReq, userId);
        return ApiResponse.success(ApiCode.SUCCESS, orderId);
    }

    @PostMapping("/reward")
    public ApiResponse<Long> saveReward(@RequestBody List<UpcyclingReq> upcyclingReqs,
                                        HttpServletRequest req) {
        String token = jwtTokenProvider.getJwtFromRequest(req);
        Long userId = jwtTokenProvider.getUserPk(token);

        Long orderId = mainService.saveReward(upcyclingReqs, userId);
        return ApiResponse.success(ApiCode.SUCCESS, orderId);
    }

}
