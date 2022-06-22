package com.ecotrade.mstrade.domain.dto;

import com.ecotrade.mstrade.domain.enuns.AdvertisementStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AdvertisementSingleOfferDTO implements Serializable {
    private final static long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String product;
    private String tradePossibility;
    private String description;
    private AdvertisementStatus status;
    private List<String> categories;
    private List<String> photosUrl;
    private OfferPhotosDTO offer;
    private UserFullDTO advertiser;
}
