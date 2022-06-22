package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserFullDTO implements Serializable {
    private static final long serialVersionUID = 1l;

    private Long id;
    private String email;
    private String name;
    private Double reliability;
    private String state;
    private String city;
    private String url;
}
