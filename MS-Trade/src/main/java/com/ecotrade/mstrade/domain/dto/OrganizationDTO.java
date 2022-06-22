package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrganizationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String photoUrl;
    private String name;
    private String description;
    private String link;

}
