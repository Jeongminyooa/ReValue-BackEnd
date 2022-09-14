package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.project.DonationRewardReq;
import kbsc.greenFunding.dto.project.DonationRewardRes;
import kbsc.greenFunding.dto.project.ProjectDonationInfoRes;
import kbsc.greenFunding.dto.response.ErrorCode;
import kbsc.greenFunding.entity.Donation;
import kbsc.greenFunding.entity.DonationMethod;
import kbsc.greenFunding.entity.Project;
import kbsc.greenFunding.exception.NoEnumException;
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

    @Transactional(readOnly = true)
    public ProjectDonationInfoRes getProjectDonationInfo(Long projectId) {
        Project project = projectJpaRepo.findById(projectId).orElseThrow();

        ProjectDonationInfoRes res = new ProjectDonationInfoRes();
        res.setCategory(project.getCategory().name());
        res.setTotalWeight(project.getDonation().getTotalWeight());

        return res;
    }

    @Transactional
    public DonationRewardRes postDonationReward(Long projectId, DonationRewardReq donationRewardReq) {
        Project project = projectJpaRepo.findById(projectId).orElseThrow();
        Donation donation = project.getDonation();

        try {
            donation.update(donationRewardReq.getDescription(),
                    donationRewardReq.getMinWeight(),
                    donationRewardReq.getAddress(),
                    DonationMethod.valueOf(donationRewardReq.getDonationMethod()));
        } catch(IllegalArgumentException e) {
            throw new NoEnumException("no enum", ErrorCode.NO_ENUM_CONSTANT);
        }
        return new DonationRewardRes(project.getId(), donation.getId());
    }

}
