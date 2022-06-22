package com.ecotrade.mstrade.domain.dto;

import com.ecotrade.mstrade.domain.enuns.AdvertisementStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ReliabilityDTO implements Serializable {
    private final static long serialVersionUID = 1L;

    public ReliabilityDTO(Integer quantity, AdvertisementStatus status) {
        this.quantity = quantity;
        this.status = status;
    }

    private Integer quantity;
    private AdvertisementStatus status;
}
