package kbsc.greenFunding.service;

import com.sun.jdi.LongValue;
import kbsc.greenFunding.dto.main.*;
import kbsc.greenFunding.entity.*;
import kbsc.greenFunding.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final DonationOrderJpaRepository donationOrderJpaRepository;
    private final UpcyclingOrderJpaRepository upcyclingOrderJpaRepository;
    private final ProjectRepository projectRepository;
    private final DonationJpaRepository donationJpaRepository;
    private final UpcyclingJpaRepository upcyclingJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final UpcyclingOrderItemJpaRepository upcyclingOrderItemJpaRepository;

    public MainListRes getMainList(ProjectType projectType, MaterialCategory category) {
        MainProjectListRes.MainProjectListResBuilder projectBuilder = MainProjectListRes.builder();
        List<MainProjectListRes> projectResList = new ArrayList<>();

        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

        Long donationCount = donationOrderJpaRepository.countAllByOrderDateBetween(startDate, endDate);
        Long upcyclingCount = upcyclingOrderJpaRepository.countAllByOrderDateBetween(startDate, endDate);
        Long totalCount = donationCount + upcyclingCount;

        /**
         * projectType, category에 따라서 projects 리스트 불러오기
         */
        List<Project> projects = new ArrayList<>();
        if(projectType.equals(ProjectType.ALL)) {
            if(category.equals(MaterialCategory.ALL)) {
                projects = projectRepository.findAllWithDonation();
            }
            else {
                projects = projectRepository.findALlWithDonationByCategory(category);
            }
        }
        else {
            if(category.equals(MaterialCategory.ALL)) { // 소재 유형 - 전체보기
                projects = projectRepository.findAllWithDonationByType(projectType);
            }
            else { // 소재에 따라 검색
                projects = projectRepository.findAllWithDonationByTypeAndCategory(projectType, category);
            }
        }

        int donationRate = 0, rewardRate = 0;

        for(Project project : projects) {
            ProjectType type = project.getProjectType();
            // System.out.println("Period = " + Duration.between(LocalDateTime.now(), project.getEndDate()).toDays());
            projectBuilder.projectId(project.getId()).category(project.getCategory()).projectType(type)
                    .thumbnail(project.getThumbnail()).title(project.getTitle()).summary(project.getSummary())
                    .remainingDate(Duration.between(LocalDateTime.now(), project.getEndDate()).toDays());
            if(type.equals(ProjectType.ALL)) { // 기부 & 리워드
                // 기부 달성률 = (기부.목표 무게 - 기부.잔여무게) / 기부.목표무게 * 100
                // project의 달성률 = (목표금액 - 잔여금액) / 목표금액 * 100
                Donation donation = project.getDonation();
                rewardRate = ((project.getAmount() - project.getRemainingAmount()) * 100) / project.getAmount();
                donationRate = ((donation.getTotalWeight() - donation.getRemainingWeight()) * 100) / donation.getTotalWeight();
            }
            else if(type.equals(ProjectType.DONATION)) { // 기부만
                Donation donation = project.getDonation();
                donationRate = ((donation.getTotalWeight() - donation.getRemainingWeight()) * 100) / donation.getTotalWeight();
            }
            else if(type.equals(ProjectType.REWARD)){ // 리워드만
                // project의 달성률 = (목표금액 - 잔여금액) / 목표금액 * 100
                rewardRate = ((project.getAmount() - project.getRemainingAmount()) * 100) / project.getAmount();
            }
            projectBuilder.donationRate(donationRate).rewardRate(rewardRate);
            projectResList.add(projectBuilder.build());
        }

        MainListRes result = MainListRes.builder().totalCount(totalCount).projectList(projectResList).build();
        return result;
    }

    public ProjectDetailRes getProjectDetail(Long projectId) {
        ProjectDetailRes.ProjectDetailResBuilder projectBuilder = ProjectDetailRes.builder();

        Project project = projectRepository.getProjectDetail(projectId);

        Long donationParticipants = Long.valueOf(0);

        int rewardRemainingCount = 0; // 리워드 잔여 수량
        int rewardTotalCount = 0;

        List<Upcycling> upcyclings = project.getUpcyclingList();
        List<UpcyclingDetailRes> rewardDetailResList = new ArrayList<>();

        for(Upcycling upcycling : upcyclings) {
            rewardDetailResList.add(
                    UpcyclingDetailRes.builder()
                    .rewardId(upcycling.getId()).title(upcycling.getTitle()).description(upcycling.getDescription())
                    .price(upcycling.getPrice()).totalCount(upcycling.getTotalCount()).remainingCount(upcycling.getRemainingCount())
                    .build());
            rewardRemainingCount += upcycling.getRemainingCount();
            rewardTotalCount += upcycling.getTotalCount();
        }

        projectBuilder
                .projectId(project.getId()).projectType(project.getProjectType())
                .thumbnail(project.getThumbnail()).title(project.getTitle()).content(project.getContent())
                .startDate(project.getStartDate()).endDate(project.getEndDate()).category(project.getCategory())
                .user(project.getUser().getName())
                .rewardAmount(project.getAmount()).rewardRemainingCount(rewardRemainingCount).rewardTotalCount(rewardTotalCount)
                .rewardDetail(rewardDetailResList);

        if(!project.getProjectType().equals(ProjectType.REWARD)) { //Donation을 체크해야하는 경우
            // 기부[기부ID, 기부 최소 단위, 기부 방법]
            Donation donation = project.getDonation();
            donationParticipants = donationOrderJpaRepository.countAllByDonation(donation);
            projectBuilder.donationDetail(
                    DonationDetailRes.builder().donationId(donation.getId())
                    .method(donation.getMethod()).minWeight(donation.getMinWeight()).address(donation.getAddress()).build()
            );
            projectBuilder.donationTotalWeight(donation.getTotalWeight()).donationRemainingWeight(donation.getRemainingWeight()).donationParticipants(donationParticipants);
        }

        ProjectDetailRes projectDetailRes = projectBuilder.build();
        return projectDetailRes;
    }

    public Long saveDonation(DonationReq donationReq) {
        Donation donation = donationJpaRepository.findById(donationReq.getDonationId())
                .orElseThrow();

        DonationOrder order = DonationOrder.builder()
                .weight(donationReq.getWeight())
                .orderDate(LocalDateTime.now())
                .donation(donation).build();
                // user 아직 안 넣음

        // 잔여 무게 -= 현재 신청 무게 하기
        donation.setRemainingWeight(donation.getRemainingWeight() - donationReq.getWeight());
        donationOrderJpaRepository.save(order);
        return order.getId();
    }

    // 리워드 신청 시
    /**
     * project에 있는 remainingAmount 계산 해주기
     * 리워드별 잔여 수량(remainingCount) 계산
     */
    @Transactional
    public Long saveReward(List<UpcyclingReq> upcyclingReqs) {
        Long userId = Long.valueOf(1);
        User user = userJpaRepository.findById(userId)
                .orElseThrow();

        int totalAmount = 0;
        // (user, 오늘 날짜)로 upcyclingOrder 생성
        UpcyclingOrder order = UpcyclingOrder.builder().user(user).orderDate(LocalDateTime.now()).build();
        upcyclingOrderJpaRepository.save(order);

        // List<UpcyclingOrderItem> orderItemList = new ArrayList<>();
        Project project = null;
        for(UpcyclingReq upcyclingReq : upcyclingReqs) {
            // upcycling 조회
            Upcycling upcycling = upcyclingJpaRepository.findById(upcyclingReq.getUpcyclingId())
                    .orElseThrow();
            if(project == null) { // 프로젝트 업데이트는 한번만
                project = upcycling.getProject();
            }
            // upcyclingOrderItem 만들기
            UpcyclingOrderItem orderItem
                    = UpcyclingOrderItem.builder().upcycling(upcycling).count(upcyclingReq.getCount()).build();

            upcyclingOrderItemJpaRepository.save(orderItem);
            upcycling.updateRemainingCount(upcyclingReq.getCount()); // 리워드별 잔여수량 update
            totalAmount += upcycling.getPrice() * upcyclingReq.getCount(); // project별 totalAmount 계산

            order.addOrderItemList(orderItem); // UpcyclingOrder에 item 추가
        }
        System.out.println("totalAmount = " + totalAmount);
        // project의 totalAmount를 update
        project.updateRemainingAmount(totalAmount);

        return order.getId();
    }
}
