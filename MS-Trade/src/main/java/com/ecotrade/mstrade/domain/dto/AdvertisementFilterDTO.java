package com.ecotrade.mstrade.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementFilterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String product;
    private String description;
    private String tradePossibility;
    private String category;
}
