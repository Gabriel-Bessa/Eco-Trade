package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OfferDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String product;
    private String description;
    private Long advertisementId;
    private BasicDTO offerer;
    private List<String> photosUrl;
}
