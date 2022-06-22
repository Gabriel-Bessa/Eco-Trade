package com.ecotrade.mstrade.service.interfaces;

import com.ecotrade.mstrade.config.errors.BusinessException;
import com.ecotrade.mstrade.config.responses.ResponseDTO;

public interface BasicService {

    BusinessException getException(String message);

    ResponseDTO getSuccess(String message);
}
