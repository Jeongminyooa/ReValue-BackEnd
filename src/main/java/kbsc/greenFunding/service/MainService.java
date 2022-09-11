package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.main.*;
import kbsc.greenFunding.entity.*;
import kbsc.greenFunding.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final DonationOrderJpaRepository donationOrderJpaRepository;
    private final UpcyclingOrderJpaRepository upcyclingOrderJpaRepository;
    private final ProjectRepository projectRepository;
    private final ProjectJpaRepository projectJpaRepository;
    private final DonationJpaRepository donationJpaRepository;

    public MainListRes getMainList() {
        MainProjectListRes.MainProjectListResBuilder projectBuilder = MainProjectListRes.builder();
        List<MainProjectListRes> projectResList = new ArrayList<>();

        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

        Long donationCount = donationOrderJpaRepository.countAllByOrderDateBetween(startDate, endDate);
        Long upcyclingCount = upcyclingOrderJpaRepository.countAllByOrderDateBetween(startDate, endDate);
        Long totalCount = donationCount + upcyclingCount;

        List<Project> projects = projectJpaRepository.findAll();

        /**
         *     private int donationRate, int rewardRate;
         */

        for(Project project : projects) {
            ProjectType type = project.getProjectType();
            System.out.println("Period = " + Duration.between(LocalDateTime.now(), project.getEndDate()).toDays());
            projectBuilder.projectId(project.getId()).category(project.getCategory()).projectType(type)
                    .thumbnail(project.getThumbnail()).title(project.getTitle()).content(project.getContent())
                    .remainingDate(Duration.between(LocalDateTime.now(), project.getEndDate()).toDays());

            if(type.equals(ProjectType.ALL)) { // 기부 & 리워드
            }
            else if(type.equals(ProjectType.DONATION)) { // 기부만

            }
            else if(type.equals(ProjectType.REWARD)){ // 리워드만

            }
            projectResList.add(projectBuilder.build());
        }

        MainListRes result = MainListRes.builder().totalCount(totalCount).projectList(projectResList).build();
        return result;
    }

    public ProjectDetailRes getProjectDetail(Long projectId) {
        ProjectDetailRes.ProjectDetailResBuilder projectBuilder = ProjectDetailRes.builder();

        Project project = projectRepository.getProjectDetail(projectId);

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
            projectBuilder.donationDetail(
                    DonationDetailRes.builder().donationId(donation.getId())
                    .method(donation.getMethod()).minWeight(donation.getMinWeight()).build()
            );
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
}
