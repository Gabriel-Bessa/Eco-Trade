package com.ecotrade.mstrade.repository;

import com.ecotrade.mstrade.domain.Advertisement;
import com.ecotrade.mstrade.domain.dto.ReliabilityDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>, JpaSpecificationExecutor<Advertisement> {
    Boolean existsAdvertisementByAdvertiser_EmailAndId(String email, Long id);

    @Query(value = "SELECT count(adv.status) as quantity, adv.status as status FROM trade.tb_advertisement adv " +
            "      INNER JOIN trade.tb_user tu on tu.id = adv.advertiser_id " +
            "      INNER JOIN trade.tb_offer offer on offer.id = adv.accepted_offer " +
            "WHERE tu.id = :userId AND adv.status != 'OPEN' AND adv.status != 'CLOSE' GROUP BY adv.status", nativeQuery = true)
    List<Tuple> reliabilityByUser(@Param("userId") Long userId);
}
