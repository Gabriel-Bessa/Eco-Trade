package com.ecotrade.mstrade.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class UserPasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String confirmPassword;
    @NotBlank
    private String newPassword;
}
