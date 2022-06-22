package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OfferAdvertisementDTO extends OfferSimpleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private BasicDTO advertisement;
    private List<String> photosUrl;
    private UserSimpleDTO offerer;
}
