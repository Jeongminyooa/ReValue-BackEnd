package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.response.ErrorCode;
import kbsc.greenFunding.dto.user.MyPageProjectRes;
import kbsc.greenFunding.dto.user.MyPageRes;
import kbsc.greenFunding.dto.user.UserLoginReq;
import kbsc.greenFunding.dto.user.UserSignUpReq;
import kbsc.greenFunding.entity.*;
import kbsc.greenFunding.exception.NoEnumException;
import kbsc.greenFunding.jwt.JwtTokenProvider;
import kbsc.greenFunding.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserJpaRepository userJpaRepository;
    private final DonationOrderJpaRepository donationOrderJpaRepository;
    private final ProjectRepository projectRepository;
    private final UpcyclingOrderRepository upcyclingOrderRepository;

    @Transactional
    public Long join(UserSignUpReq signUpReq) {
        if(userJpaRepository.findByUserId(signUpReq.getUserId()).isPresent()) {
            // throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // pw 암호화
        String encodingPassword = bCryptPasswordEncoder.encode(signUpReq.getPw());

        User user = User.builder().userId(signUpReq.getUserId()).pw(encodingPassword)
                .name(signUpReq.getName()).birth(signUpReq.getBirth()).gender(signUpReq.getGender())
                .tel(signUpReq.getTel()).agreement(signUpReq.isAgreement())
                .signDate(LocalDateTime.now()).build();
        userJpaRepository.save(user);
        return user.getId();
    }

    /*
    public boolean checkUserId(String userId) {
        if(userJpaRepository.findByUserId(userId).isPresent()) {
            // throw new IllegalArgumentException("이미 가입된 이메일입니다.");
            return false;
        }
        return true;
    }
    */

    @Transactional
    public String login(UserLoginReq loginReq) {
        User user = userJpaRepository.findByUserId(loginReq.getId())
                .orElseThrow(() -> new IllegalArgumentException("가입된 아이디가 아닙니다."));

        // 패스워드 일치 여부 확인
        passwordMustBeSame(loginReq.getPw(), user.getPw());

        // userId (사용자 아이디)로 토큰 생성
        String token = jwtTokenProvider.makeJwtToken(user.getUserId());

        return token;
    }

    private void passwordMustBeSame(String requestPassword, String password) {
        if (!bCryptPasswordEncoder.matches(requestPassword, password)) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public MyPageRes mypage(Long userId) {
        MyPageRes.MyPageResBuilder mypageBuilder = MyPageRes.builder();

        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("가입된 사용자가 아닙니다."));
        Long activeDays = Duration.between(user.getSignDate(), LocalDateTime.now()).toDays();
        mypageBuilder.userId(user.getUserId()).activeDays(activeDays);

        List<MyPageProjectRes> donationList = new ArrayList<>();
        List<MyPageProjectRes> rewardList = new ArrayList<>();

        // 기부 리스트
        List<DonationOrder> orderList = donationOrderJpaRepository.findAllByUser(user);
        List<Donation> donations = orderList.stream().map(o -> o.getDonation()).collect(Collectors.toList());

        List<Project> allByDonation = projectRepository.findAllByDonation(donations);
        donationList = allByDonation.stream()
                .map(p -> MyPageProjectRes.builder()
                        .projectId(p.getId()).category(p.getCategory()).title(p.getTitle())
                        .summary(p.getSummary()).thumbnail(p.getThumbnail()).build())
                .collect(Collectors.toList());

        mypageBuilder.donationList(donationList);

        // 리워드 리스트
        List<UpcyclingOrder> orderWithItemByUser = upcyclingOrderRepository.getOrderWithItemByUser(user);
        List<Upcycling> upcyclingList = new ArrayList<>();

        for(UpcyclingOrder order : orderWithItemByUser) {
            List<UpcyclingOrderItem> orderItemList = order.getOrderItemList();
            upcyclingList.addAll(
                orderItemList.stream()
                        .map(il -> il.getUpcycling()).collect(Collectors.toList())
            );
        }

        List<Project> projects = upcyclingList.stream().map(
                                    up -> up.getProject()).collect(Collectors.toList());
        projects = projects.stream().distinct().collect(Collectors.toList());
        rewardList = projects.stream()
                .map(p -> MyPageProjectRes.builder()
                        .projectId(p.getId()).category(p.getCategory()).title(p.getTitle())
                        .summary(p.getSummary()).thumbnail(p.getThumbnail()).build())
                .collect(Collectors.toList());

        mypageBuilder.rewardList(rewardList);

        return mypageBuilder.build();
    }
}
