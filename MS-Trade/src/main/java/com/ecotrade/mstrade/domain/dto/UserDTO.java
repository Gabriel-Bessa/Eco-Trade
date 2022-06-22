package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class UserDTO extends UserCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cep;
    private String address;
    private String number;
    private String city;
    private String state;
    private String phone;
    private Long id;
    private String url;
    private Double ecopoints;

}
