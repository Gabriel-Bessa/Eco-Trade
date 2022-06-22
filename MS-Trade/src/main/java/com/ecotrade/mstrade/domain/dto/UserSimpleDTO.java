package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserSimpleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;
    @Email
    @NotNull
    private String email;
    @NotNull
    private LocalDate birthDate;
}
