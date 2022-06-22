package com.ecotrade.mstrade.config.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Locale;
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
        Locale.setDefault(new Locale("pt", "BR"));
        this.property = getKey(key);
        this.message = getMessage(value);
    }

    private String getKey(String key) {
        // TODO: refactor não está pegando o arquivo correto
        return ResourceBundle.getBundle("menssage/message_default", Locale.getDefault()).getString(key);
    }

    private String getMessage(String value) {
        return ResourceBundle.getBundle("menssage/message_success",Locale.getDefault()).getString(value);
    }
}
