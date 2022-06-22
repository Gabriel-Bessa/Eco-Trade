package com.ecotrade.mswebsocket.config;

import com.ecotrade.mswebsocket.config.errors.BusinessException;
import com.ecotrade.mswebsocket.config.errors.NotAutorizedExcetion;
import com.ecotrade.mswebsocket.config.errors.dto.FieldErrorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationErrorHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FieldErrorDTO> error(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        return fieldErrors.stream().map(error -> new FieldErrorDTO(error.getField(), error.getDefaultMessage())).collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public FieldErrorDTO errorBusiness(BusinessException exception) {
        return new FieldErrorDTO(exception.getProperty(), exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAutorizedExcetion.class)
    public List<FieldErrorDTO> errorBusiness(NotAutorizedExcetion exception) {
        FieldErrorDTO error = new FieldErrorDTO(exception.getMessage(), exception.getDescription());
        return Collections.singletonList(error);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<FieldErrorDTO> errorBusiness(HttpMessageNotReadableException exception) {
        FieldErrorDTO error = new FieldErrorDTO(exception.getMessage(), exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<FieldErrorDTO> methodNoSupported(HttpRequestMethodNotSupportedException exception) {
        FieldErrorDTO error = new FieldErrorDTO(exception.getMessage(), String.format("O método %s para esse endpoint não é suportador pelo sistema", exception.getMethod()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<FieldErrorDTO> feignError(FeignException.BadRequest exception) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> erros = objectMapper.readValue(exception.responseBody().get().array(), Map.class);
        FieldErrorDTO error = new FieldErrorDTO(erros.get("field"), erros.get("msg"));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
