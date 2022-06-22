package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(1)
    private Long id;
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
    @NotBlank
    private String name;
    @Email
    @NotNull
    private String email;
    @NotNull
    private LocalDate birthDate;
}
