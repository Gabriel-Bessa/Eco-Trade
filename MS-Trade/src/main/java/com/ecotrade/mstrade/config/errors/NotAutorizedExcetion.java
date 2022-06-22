package com.ecotrade.mstrade.config.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotAutorizedExcetion extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private String description;
}
