package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.project.ProjectDonationInfoRes;
import kbsc.greenFunding.dto.project.ProjectInfoReq;
import kbsc.greenFunding.dto.project.ProjectPlanReq;
import kbsc.greenFunding.dto.project.ProjectTypeReq;
import kbsc.greenFunding.dto.response.ErrorCode;
import kbsc.greenFunding.entity.*;
import kbsc.greenFunding.exception.NoEnumException;
import kbsc.greenFunding.repository.DonationJpaRepository;
import kbsc.greenFunding.repository.ProjectJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectJpaRepository projectJpaRepo;
    private final DonationJpaRepository donationJpaRepo;

    // 프로젝트 type, category 저장
    @Transactional(rollbackFor=Exception.class)
    public Long postProjectType(Long userId, ProjectTypeReq projectTypeReq) {
        try {
            // User user = userJpaRepo.findById(userId).orElseThrow();

            Project project = Project.projectTypeBuilder()
                    .projectType(ProjectType.valueOf(projectTypeReq.getProjectType()))
                    .category(MaterialCategory.valueOf(projectTypeReq.getCategory()))
                    .build();

            Project projectId = projectJpaRepo.save(project);

            return projectId.getId();
        } catch(IllegalArgumentException e) {
            throw new NoEnumException("no enum", ErrorCode.NO_ENUM_CONSTANT);
        }
    }

    // 프로젝트 info 저장
    @Transactional(rollbackFor=Exception.class)
    public Long postProjectInfo(ProjectInfoReq projectInfoReq, String imageName, Long projectId) {
        StringBuilder imageUrl = new StringBuilder();
        imageUrl.append("https://revalue.s3.us-west-2.amazonaws.com/");
        imageUrl.append(imageName);

        Project project = projectJpaRepo.findById(projectId).orElseThrow();

        project.updateProjectInfo(projectInfoReq.getTitle(), imageUrl.toString(), projectInfoReq.getContent());

        return project.getId();
    }

    // 프로젝트 plan 저장
    @Transactional(rollbackFor=Exception.class)
    public Long postProjectPlan(ProjectPlanReq projectPlanReq, Long projectId) {

        Project project = projectJpaRepo.findById(projectId).orElseThrow();

        if(project.getProjectType() == ProjectType.ALL) {
            // 기존 정보가 있는지 확인
            if(project.getDonation() != null) {
                project.getDonation().updateTotalWeight(projectPlanReq.getTotalWeight());
            } else {
                Donation donation = Donation.donationBuilder()
                        .totalWeight(projectPlanReq.getTotalWeight())
                        .build();

                donationJpaRepo.save(donation);
                project.setDonation(donation);
            }

            project.updateProjectPlan(projectPlanReq.getStartDate(), projectPlanReq.getEndDate());
            project.updateAmount(projectPlanReq.getAmount(), projectPlanReq.getAmount());
        } else if(project.getProjectType() == ProjectType.DONATION) {
            // 기존 정보가 있는지 확인
            if(project.getDonation() != null) {
                project.getDonation().updateTotalWeight(projectPlanReq.getTotalWeight());
            } else {
                Donation donation = Donation.donationBuilder()
                        .totalWeight(projectPlanReq.getTotalWeight())
                        .build();
                donationJpaRepo.save(donation);

                project.setDonation(donation);
            }

            project.updateProjectPlan(projectPlanReq.getStartDate(), projectPlanReq.getEndDate());
        } else {
            project.updateAmount(projectPlanReq.getAmount(), projectPlanReq.getAmount());
        }
        return project.getId();
    }
}
