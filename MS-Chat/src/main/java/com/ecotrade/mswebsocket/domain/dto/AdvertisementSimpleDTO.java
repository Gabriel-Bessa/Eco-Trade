package com.ecotrade.mswebsocket.domain.dto;

import com.ecotrade.mswebsocket.domain.enuns.AdvertisementStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AdvertisementSimpleDTO implements Serializable {
    private final static long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String product;
    private String tradePossibility;
    private String description;
    private AdvertisementStatus status;
    private List<String> categories;
    private List<String> photosUrl;
    private UserFullDTO advertiser;
}
