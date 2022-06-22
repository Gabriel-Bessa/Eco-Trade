package com.ecotrade.mstrade.repository;

import com.ecotrade.mstrade.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {

    Boolean existsByIdAndOffererEmail(Long id, String email);

    List<Offer> findAllByAdvertisement_Id(Long id);

    @Query("SELECT offer FROM Offer offer " +
            "WHERE offer.advertisement.advertiser.email = :email " +
            "OR offer.offerer.email = :email")
    List<Offer> findAllByEmailOrAdvertisementEmail(String email);

    Optional<Offer> findByIdAndAdvertisementId(Long id, Long advertisementId);
}
