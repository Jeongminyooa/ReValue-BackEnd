package kbsc.greenFunding.service;

import kbsc.greenFunding.entity.Project;
import kbsc.greenFunding.repository.DonationRepository;
import kbsc.greenFunding.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonationService {

    private final DonationRepository donationRepository;
    private final ProjectRepository projectRepository;


}
