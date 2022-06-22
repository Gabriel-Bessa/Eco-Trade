package com.ecotrade.mstrade.repository;

import com.ecotrade.mstrade.domain.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    Boolean existsAppUsersByEmailOrPhone(String email, String phone);

    @Query(value = "SELECT count(adv.status) as quantity, adv.status as status FROM trade.tb_advertisement adv " +
            "      INNER JOIN trade.tb_user tu on tu.id = adv.advertiser_id " +
            "      INNER JOIN trade.tb_offer offer on offer.id = adv.accepted_offer " +
            "WHERE tu.id = :userId AND adv.status != 'OPEN' AND adv.status != 'CLOSE' GROUP BY adv.status", nativeQuery = true)
    List<Tuple> reliabilityByUser(@Param("userId") Long userId);
}
