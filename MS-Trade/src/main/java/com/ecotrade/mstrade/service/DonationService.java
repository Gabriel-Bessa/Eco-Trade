package com.ecotrade.mstrade.service;

import com.ecotrade.mstrade.config.errors.BusinessException;
import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.domain.Donation;
import com.ecotrade.mstrade.domain.Organization;
import com.ecotrade.mstrade.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DonationService {

    private final OrganizationService organizationService;
    private final DonationRepository repository;
    private final UserService service;

    @Transactional
    public ResponseDTO donate(Integer quantity, Long orgId) {
        if (quantity.doubleValue() > service.getEcopoints()) {
            throw new BusinessException("donation.exception", "donation.dont.have.quantity");
        }
        Organization organization = organizationService.getById(orgId);
        Donation donation = Donation.builder().day(LocalDateTime.now()).quantity(quantity.doubleValue()).organization(organization).build();
        repository.save(donation);
        return new ResponseDTO("donation.success", "donation.success");
    }

}
