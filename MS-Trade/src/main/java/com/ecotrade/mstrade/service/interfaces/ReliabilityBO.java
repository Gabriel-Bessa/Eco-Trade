package com.ecotrade.mstrade.service.interfaces;

import com.ecotrade.mstrade.domain.enuns.AdvertisementStatus;

import java.util.Map;
import java.util.Optional;

public interface ReliabilityBO {

    default Double calcReliability(Map<AdvertisementStatus, Integer> map) {
        map.put(AdvertisementStatus.ACCEPTED, Optional.ofNullable(map.get(AdvertisementStatus.ACCEPTED)).orElse(0));
        map.put(AdvertisementStatus.RECIVED, Optional.ofNullable(map.get(AdvertisementStatus.RECIVED)).orElse(0));
        if (map.get(AdvertisementStatus.RECIVED) == 0D && map.get(AdvertisementStatus.ACCEPTED) == 0 ) {
            return 0D;
        }
        return ((double) map.get(AdvertisementStatus.RECIVED) / (map.get(AdvertisementStatus.ACCEPTED) + map.get(AdvertisementStatus.RECIVED)))*100;
    }

}
