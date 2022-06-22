package com.ecotrade.mstrade.service;

import com.ecotrade.mstrade.config.errors.BusinessException;
import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.config.security.SecurityUtils;
import com.ecotrade.mstrade.domain.Advertisement;
import com.ecotrade.mstrade.domain.enuns.AdvertisementStatus;
import com.ecotrade.mstrade.repository.AdvertisementRepository;
import com.ecotrade.mstrade.service.interfaces.BasicService;
import com.ecotrade.mstrade.service.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvertisementMiddlerService implements BasicService {
    private final AdvertisementRepository repository;

    public Advertisement findById(Long id) {
        return repository.findById(id).orElseThrow(() -> getException("advertisement.not.found"));
    }

    public Map<AdvertisementStatus, Integer> getReliabilityOfAuthUser() {
        List<Tuple> tuples = repository.reliabilityByUser(SecurityUtils.getAuthUser().getId());
        Map<AdvertisementStatus, Integer> reliabilities = new HashMap<>();
        tuples.forEach(it -> reliabilities.put(AdvertisementStatus.valueOf((String) it.get(1)), ((BigInteger) it.get(0)).intValue()));
        return reliabilities;
    }

    @Override
    public BusinessException getException(String message) {
        return new BusinessException(MessageUtils.ADVERTISEMENT_EXCEPTION, message);
    }

    @Override
    public ResponseDTO getSuccess(String message) {
        return new ResponseDTO(MessageUtils.ADVERTISEMENT_SUCCESS, message);
    }

}
