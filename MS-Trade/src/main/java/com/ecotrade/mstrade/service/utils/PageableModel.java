package com.ecotrade.mstrade.service.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Getter
@Setter
public class PageableModel<T> {
    @NotNull
    @Min(0)
    private Integer page;
    @NotNull
    @Min(20)
    @Max(100)
    private Integer pageSize;
    @NotNull
    private Sort.Direction sortBy;

    private String field;
    private T filter;

    public Optional<T> getOptFilter() {
        return Optional.ofNullable(filter);
    }

    public Pageable getPageable() {
        if (field != null) {
            return PageRequest.of(page, pageSize, Sort.by(sortBy, field));
        }
        return PageRequest.of(page, pageSize);
    }
}
