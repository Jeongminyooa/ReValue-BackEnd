package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.project.DonationRewardReq;
import kbsc.greenFunding.dto.project.ProjectDonationInfoRes;
import kbsc.greenFunding.entity.Project;
import kbsc.greenFunding.repository.DonationJpaRepository;
import kbsc.greenFunding.repository.ProjectJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonationService {

    private final DonationJpaRepository donationJpaRepo;
    private final ProjectJpaRepository projectJpaRepo;

    @Transactional
    public ProjectDonationInfoRes getProjectDonationInfo(Long projectId) {
        Project project = projectJpaRepo.findById(projectId).orElseThrow();

        ProjectDonationInfoRes res = new ProjectDonationInfoRes();
        res.setCategory(project.getCategory().name());
        res.setTotalWeight(project.getDonation().getTotalWeight());

        return res;
    }

}
