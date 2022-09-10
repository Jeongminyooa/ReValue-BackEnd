package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.main.MainListRes;
import kbsc.greenFunding.dto.main.ProjectDetailRes;
import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final MainService mainService;

    @GetMapping("")
    public ApiResponse<MainListRes> getMainList() {
        MainListRes mainList = mainService.getMainList();

        return ApiResponse.success(ApiCode.SUCCESS, mainList);
    }

}
