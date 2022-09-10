package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.main.MainListRes;
import kbsc.greenFunding.repository.DonationOrderJpaRepository;
import kbsc.greenFunding.repository.DonationOrderRepository;
import kbsc.greenFunding.repository.UpcyclingOrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class MainService {

    private final DonationOrderJpaRepository donationOrderJpaRepository;
    private final DonationOrderRepository donationOrderRepository;

    private final UpcyclingOrderJpaRepository upcyclingOrderJpaRepository;

    public MainListRes getMainList() {
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        System.out.println("startDate = " + startDate + "endDate = " + endDate);

        Long donationCount = donationOrderJpaRepository.countAllByOrderDateBetween(startDate, endDate);
        System.out.println("donationCount = " + donationCount);
        
        Long upcyclingCount = upcyclingOrderJpaRepository.countAllByOrderDateBetween(startDate, endDate);
        System.out.println("upcyclingCount = " + upcyclingCount);
        
        Long totalCount = donationCount + upcyclingCount;




        MainListRes result = MainListRes.builder().totalCount(totalCount).build();
        return result;
    }
}
