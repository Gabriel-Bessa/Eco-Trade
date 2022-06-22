package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class OfferSimpleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    private Long id;
    @NotBlank
    private String product;
    private String status;
    @NotBlank
    private String description;
    @NotNull
    @Min(1)
    private Long advertisementId;
}
