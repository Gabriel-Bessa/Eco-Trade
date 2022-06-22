package com.ecotrade.mswebsocket.config.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ResourceBundle;


@Getter
@Setter
@Component
@NoArgsConstructor
public class ResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String property;
    private String message;

    public ResponseDTO(String key, String value) {
        this.property = getKey(key);
        this.message = getMessage(value);
    }

    private String getKey(String key) {
        // TODO: refactor não está pegando o arquivo correto
        return ResourceBundle.getBundle("menssage/message_default", LocaleContextHolder.getLocale()).getString(key);
    }

    private String getMessage(String value) {
        return ResourceBundle.getBundle("menssage/message_success", LocaleContextHolder.getLocale()).getString(value);
    }
}
