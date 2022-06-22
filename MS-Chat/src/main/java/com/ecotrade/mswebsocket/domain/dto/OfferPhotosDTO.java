package com.ecotrade.mswebsocket.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OfferPhotosDTO extends OfferSimpleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> photosUrl;
    private UserFullDTO offerer;
}
