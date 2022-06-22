package com.ecotrade.mswebsocket.domain.dto;

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

    private Long id;
    private String product;
    private String status;
    private String description;
    private Long advertisementId;
}
