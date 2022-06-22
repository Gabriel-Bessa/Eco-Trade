package com.ecotrade.mswebsocket.config.errors.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class FieldErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String field;
    private String msg;

    public FieldErrorDTO(String field, String msg) {
        this.field = field;
        this.msg = msg;
    }
}
