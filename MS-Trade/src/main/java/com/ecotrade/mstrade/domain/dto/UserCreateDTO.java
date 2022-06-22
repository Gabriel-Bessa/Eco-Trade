package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class UserCreateDTO extends UserSimpleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String password;
    @NotBlank
    @Length(max = 50)
    private String cep;
    @NotBlank
    @Length(max = 50)
    private String address;
    @NotBlank
    @Length(max = 10)
    private String number;
    @NotBlank
    @Length(max = 50)
    private String city;
    @NotBlank
    @Length(max = 2)
    private String state;
    @NotBlank
    @Length(min = 10, max = 20)
    private String phone;
}
