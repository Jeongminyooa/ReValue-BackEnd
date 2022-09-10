package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.project.ProjectInfoReq;
import kbsc.greenFunding.dto.project.ProjectPlanReq;
import kbsc.greenFunding.dto.project.ProjectTypeReq;
import kbsc.greenFunding.dto.response.ErrorCode;
import kbsc.greenFunding.entity.Donation;
import kbsc.greenFunding.entity.MaterialCategory;
import kbsc.greenFunding.entity.Project;
import kbsc.greenFunding.entity.ProjectType;
import kbsc.greenFunding.exception.NoEnumException;
import kbsc.greenFunding.repository.DonationRepository;
import kbsc.greenFunding.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepo;
    private final DonationRepository donationRepository;

    // 프로젝트 type, category 저장
    @Transactional(rollbackFor=Exception.class)
    public Long postProjectType(Long userId, ProjectTypeReq projectTypeReq) {
        try {
            // User user = userRepository.findById(userId).orElseThrow();

            Project project = Project.projectTypeBuilder()
                    .projectType(ProjectType.valueOf(projectTypeReq.getProjectType()))
                    .category(MaterialCategory.valueOf(projectTypeReq.getCategory()))
                    .build();

            Project projectId = projectRepo.save(project);

            return projectId.getId();
        } catch(IllegalArgumentException e) {
            throw new NoEnumException("no enum", ErrorCode.NO_ENUM_CONSTANT);
        }
    }

    // 프로젝트 info 저장
    @Transactional(rollbackFor=Exception.class)
    public Long postProjectInfo(ProjectInfoReq projectInfoReq, String imageUrl, Long projectId) {
       Project project = projectRepo.findById(projectId).orElseThrow();

       project.updateProjectInfo(projectInfoReq.getTitle(), imageUrl, projectInfoReq.getContent());

       return project.getId();
    }

    @Transactional(rollbackFor=Exception.class)
    public Long postProjectPlan(ProjectPlanReq projectPlanReq, Long projectId) {

        Project project = projectRepo.findById(projectId).orElseThrow();

        if(project.getProjectType() == ProjectType.ALL) {

        }
        // 기부 / 기부&판매 / 판매


        Donation donation = Donation.donaionBuilder()
                .totalWeight(projectPlanReq.getTotalWeight())
                .build();

        donationRepository.save(donation);

        project.setDonation(donation);
        project.updateProjectPlan(projectPlanReq.getStartDate(), projectPlanReq.getEndDate());

        return project.getId();
    }
}
