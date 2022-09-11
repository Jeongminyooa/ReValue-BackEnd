package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.project.UpcyclingRewardReq;
import kbsc.greenFunding.dto.project.UpcyclingRewardRes;
import kbsc.greenFunding.entity.Project;
import kbsc.greenFunding.entity.Upcycling;
import kbsc.greenFunding.repository.ProjectJpaRepository;
import kbsc.greenFunding.repository.UpcyclingJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpcyclingService {
    private final UpcyclingJpaRepository upcyclingJpaRepo;
    private final ProjectJpaRepository projectJpaRepo;

    @Transactional(readOnly = true)
    public UpcyclingRewardRes getUpcyclingRewardList(Long projectId) {
        Project project = projectJpaRepo.findById(projectId).orElseThrow();

        List<Upcycling> upcyclingList = project.getUpcyclingList();
        List<UpcyclingRewardRes.UpcyclingReward> upcyclingRewardList = upcyclingList.stream().map(reward -> new UpcyclingRewardRes.UpcyclingReward(
                reward.getId(),
                reward.getTitle(),
                reward.getPrice(),
                reward.getDescription(),
                reward.getTotalCount()))
                .collect(Collectors.toList());

        return new UpcyclingRewardRes(project.getId(), project.getAmount(), upcyclingRewardList);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long postUpcyclingReward(Long projectId, UpcyclingRewardReq upcyclingRewardReq) {

        Project project = projectJpaRepo.findById(projectId).orElseThrow();

        Upcycling upCycling = Upcycling.builder()
                .title(upcyclingRewardReq.getTitle())
                .price(upcyclingRewardReq.getPrice())
                .description(upcyclingRewardReq.getDescription())
                .totalCount(upcyclingRewardReq.getTotalCount())
                .remainingCount(upcyclingRewardReq.getTotalCount())
                .build();
        upCycling.setProject(project);
        upcyclingJpaRepo.save(upCycling);

        return project.getId();
    }
}
