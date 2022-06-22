package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class UserFullNoPasswordDTO extends UserSimpleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cep;
    private String address;
    private Double ecopoints;
    private String number;
    private String city;
    private String state;
    private String phone;
    private String url;
}
