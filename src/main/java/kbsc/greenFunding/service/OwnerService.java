package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.owner.OwnerProjectListRes;
import kbsc.greenFunding.dto.owner.OwnerProjectDetailRes;
import kbsc.greenFunding.entity.*;
import kbsc.greenFunding.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerService {

    private final UserJpaRepository userJpaRepo;
    private final ProjectJpaRepository projectJpaRepo;
    private final DonationOrderJpaRepository donationOrderJpaRepo;
    private final UpcyclingOrderItemRepository upcyclingOrderItemRepo;

    @Transactional(readOnly = true)
    public List<OwnerProjectListRes> getOwnerProjectList(Long ownerId) {
        OwnerProjectListRes.OwnerProjectListResBuilder projectBuilder = OwnerProjectListRes.builder();
        List<OwnerProjectListRes> projectResList = new ArrayList<>();

        User owner = userJpaRepo.findById(ownerId).orElseThrow();

        List<Project> projectList = projectJpaRepo.findByUser(owner);

        int donationRate = 0, rewardRate = 0;
        int fundingWeight = 0,fundingAmount = 0;

        for(Project project : projectList) {
            ProjectType type = project.getProjectType();

            // 프로젝트 공통 정보 세팅
            projectBuilder
                    .projectId(project.getId())
                    .title(project.getTitle())
                    .category(project.getCategory().name())
                    .type(type.name())
                    .startDate(project.getStartDate())
                    .endDate(project.getEndDate())
                    .thumbnail(project.getThumbnail());

            if(type.equals(ProjectType.ALL)) { // 기부 & 리워드
                Donation donation = project.getDonation();

                // 기부 달성률 = (기부.목표 무게 - 기부.잔여무게) / 기부.목표무게 * 100
                donationRate = ((donation.getTotalWeight() - donation.getRemainingWeight()) * 100) / donation.getTotalWeight();
                // 기부 달성량 (무게) = totalWeight - remainingWeight
                fundingWeight = donation.getTotalWeight() - donation.getRemainingWeight();

                projectBuilder
                        .donationDetailRes(
                            new OwnerProjectListRes.DonationDetailRes(donation.getTotalWeight(), donation.getMinWeight(), donationRate, fundingWeight)
                        );

                // project의 달성률 = (목표금액 - 잔여금액) / 목표금액 * 100
                rewardRate = ((project.getAmount() - project.getRemainingAmount()) * 100) / project.getAmount();
                // 업사이클링 달성량 (금액) = amount - remainingAmount
                fundingAmount = project.getAmount() - project.getRemainingAmount();

                projectBuilder
                        .upcyclingDetailRes(
                                new OwnerProjectListRes.UpcyclingDetailRes(project.getAmount(), rewardRate, fundingAmount)
                        );
            }
            else if(type.equals(ProjectType.DONATION)) { // 기부만
                Donation donation = project.getDonation();

                donationRate = ((donation.getTotalWeight() - donation.getRemainingWeight()) * 100) / donation.getTotalWeight();
                fundingWeight = donation.getTotalWeight() - donation.getRemainingWeight();

                projectBuilder
                        .donationDetailRes(
                                new OwnerProjectListRes.DonationDetailRes(donation.getTotalWeight(), donation.getMinWeight(), donationRate, fundingWeight)
                        );
            }
            else if(type.equals(ProjectType.REWARD)){ // 리워드만
                rewardRate = ((project.getAmount() - project.getRemainingAmount()) * 100) / project.getAmount();
                fundingAmount = project.getAmount() - project.getRemainingAmount();

                projectBuilder
                        .upcyclingDetailRes(
                                new OwnerProjectListRes.UpcyclingDetailRes(project.getAmount(), rewardRate, fundingAmount)
                        );
            }
            projectResList.add(projectBuilder.build());
        }
        return projectResList;
    }

    @Transactional(readOnly = true)
    public OwnerProjectDetailRes getOwnerProject(Long projectId) {
        Project project = projectJpaRepo.findById(projectId).orElseThrow();

        OwnerProjectDetailRes.OwnerProjectDetailResBuilder projectBuilder = OwnerProjectDetailRes.builder();

        int donationRate = 0, rewardRate = 0;
        int fundingWeight = 0, fundingAmount = 0;

        ProjectType type = project.getProjectType();

        // 프로젝트 소개 이미지
        List<String> imageList = new ArrayList<>();
        project.getImageList().forEach(image -> imageList.add(image.getFileUrl()));

        // 프로젝트 공통 정보 세팅
        projectBuilder
                .projectId(project.getId())
                .title(project.getTitle())
                .summary(project.getSummary())
                .category(project.getCategory().name())
                .type(type.name())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .period(ChronoUnit.DAYS.between(LocalDateTime.now(), project.getEndDate()))
                .thumbnail(project.getThumbnail())
                .content(project.getContent())
                .imageList(imageList);


        if(type.equals(ProjectType.ALL)) { // 기부 & 리워드
            Donation donation = project.getDonation();

            // 기부 달성률 = (기부.목표 무게 - 기부.잔여무게) / 기부.목표무게 * 100
            donationRate = ((donation.getTotalWeight() - donation.getRemainingWeight()) * 100) / donation.getTotalWeight();
            // 기부 달성량 (무게) = totalWeight - remainingWeight
            fundingWeight = donation.getTotalWeight() - donation.getRemainingWeight();
            // 총 서포터 수
            Long totalSupporter = donationOrderJpaRepo.countAllByDonation(donation);

            projectBuilder
                    .donationDetailRes(
                            new OwnerProjectDetailRes.DonationDetailRes(
                                    donationRate,
                                    donation.getTotalWeight(),
                                    donation.getMinWeight(),
                                    fundingWeight,
                                    totalSupporter,
                                    donation.getDescription(),
                                    donation.getMethod().name(),
                                    donation.getAddress())
                    );

            List<Upcycling> upcyclingList = project.getUpcyclingList();

            // project의 달성률 = (목표금액 - 잔여금액) / 목표금액 * 100
            rewardRate = ((project.getAmount() - project.getRemainingAmount()) * 100) / project.getAmount();
           // 업사이클링 달성량 (금액) = amount - remainingAmount
            fundingAmount = project.getAmount() - project.getRemainingAmount();

            // for( 업사이클링오더아이템 레포에서 count, groupby user, where upcycling )
            // 그럼 여기서 나오는건 user : 2(주문개수) 일테니까
            // 즉, 행의 개수가 총 서포터수가 되는 것!
            // List<Long> -> list.size() 가 총 서포터 수
            List<Long> countSupporter = new ArrayList<>();
            List<OwnerProjectDetailRes.RewardDetailRes> rewardList = new ArrayList<>();
            for (Upcycling upcycling : upcyclingList) {
                countSupporter = upcyclingOrderItemRepo.countByProjectGroupByUser(project.getId());
                rewardList.add(new OwnerProjectDetailRes.RewardDetailRes(
                        upcycling.getTitle(), upcycling.getDescription(), upcycling.getPrice(), upcycling.getTotalCount()
                ) );

            }
            totalSupporter = Long.valueOf(countSupporter.size());

            projectBuilder
                    .upcyclingDetailRes(
                            new OwnerProjectDetailRes.UpcyclingDetailRes(
                                    rewardRate,
                                    project.getAmount(),
                                    fundingAmount,
                                    totalSupporter,
                                    rewardList)
                    );
        }
        else if(type.equals(ProjectType.DONATION)) { // 기부만
            Donation donation = project.getDonation();


            // 기부 달성률 = (기부.목표 무게 - 기부.잔여무게) / 기부.목표무게 * 100
            donationRate = ((donation.getTotalWeight() - donation.getRemainingWeight()) * 100) / donation.getTotalWeight();

            // 기부 달성량 (무게) = totalWeight - remainingWeight
            fundingWeight = donation.getTotalWeight() - donation.getRemainingWeight();
            // 총 서포터 수
            Long totalSupporter = donationOrderJpaRepo.countAllByDonation(donation);

            projectBuilder
                    .donationDetailRes(
                            new OwnerProjectDetailRes.DonationDetailRes(
                                    donationRate,
                                    donation.getTotalWeight(),
                                    donation.getMinWeight(),
                                    fundingWeight,
                                    totalSupporter,
                                    donation.getDescription(),
                                    donation.getMethod().name(),
                                    donation.getAddress())
                    );

            List<Upcycling> upcyclingList = project.getUpcyclingList();
        }
        else if(type.equals(ProjectType.REWARD)){ // 리워드만
            List<Upcycling> upcyclingList = project.getUpcyclingList();

            // project의 달성률 = (목표금액 - 잔여금액) / 목표금액 * 100
            rewardRate = ((project.getAmount() - project.getRemainingAmount()) * 100) / project.getAmount();
            // 업사이클링 달성량 (금액) = amount - remainingAmount
            fundingAmount = project.getAmount() - project.getRemainingAmount();

            // for( 업사이클링오더아이템 레포에서 count, groupby user, where upcycling )
            // 그럼 여기서 나오는건 user : 2(주문개수) 일테니까
            // 즉, 행의 개수가 총 서포터수가 되는 것!
            // List<Long> -> list.size() 가 총 서포터 수
            List<Long> countSupporter = new ArrayList<>();
            List<OwnerProjectDetailRes.RewardDetailRes> rewardList = new ArrayList<>();
            for (Upcycling upcycling : upcyclingList) {
                countSupporter = upcyclingOrderItemRepo.countByProjectGroupByUser(project.getId());
                rewardList.add(new OwnerProjectDetailRes.RewardDetailRes(
                        upcycling.getTitle(), upcycling.getDescription(), upcycling.getPrice(), upcycling.getTotalCount()
                ) );

            }
            Long totalSupporter = Long.valueOf(countSupporter.size());

            projectBuilder
                    .upcyclingDetailRes(
                            new OwnerProjectDetailRes.UpcyclingDetailRes(
                                    rewardRate,
                                    project.getAmount(),
                                    fundingAmount,
                                    totalSupporter,
                                    rewardList)
                    );
        }
        return projectBuilder.build();
    }
}
