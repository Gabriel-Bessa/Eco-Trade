package com.ecotrade.mswebsocket.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
public class MessageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Length(max = 1000)
    private String text;
}
