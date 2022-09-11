package kbsc.greenFunding.service;

import kbsc.greenFunding.repository.DonationJpaRepository;
import kbsc.greenFunding.repository.ProjectJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonationService {

    private final DonationJpaRepository donationJpaRepository;
    private final ProjectJpaRepository projectRepository;


}
