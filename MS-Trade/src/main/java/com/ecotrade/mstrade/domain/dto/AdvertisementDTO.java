package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AdvertisementDTO implements Serializable {
    private final static long serialVersionUID = 1L;

    private Long id;
    @NotBlank
    @Length(max = 255)
    private String title;
    @NotBlank
    @Length(max = 255)
    private String product;
    @NotBlank
    @Length(max = 255)
    private String tradePossibility;
    @NotBlank
    @Length(max = 1000)
    private String description;
    @NotEmpty
    private List<String> categories;
}
