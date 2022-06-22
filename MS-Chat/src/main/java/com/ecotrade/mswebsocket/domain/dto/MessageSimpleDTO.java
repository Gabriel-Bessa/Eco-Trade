package com.ecotrade.mswebsocket.domain.dto;

import com.ecotrade.mswebsocket.domain.enuns.MessageStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MessageSimpleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private MessageStatus status;
    private String text;
    private LocalDateTime sendAt;

}
