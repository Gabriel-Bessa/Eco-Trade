package com.ecotrade.mswebsocket.service.mapper;

import com.ecotrade.mswebsocket.domain.Message;
import com.ecotrade.mswebsocket.domain.dto.MessageDTO;
import com.ecotrade.mswebsocket.domain.dto.MessageSimpleDTO;
import org.mapstruct.Mapper;

@Mapper
public interface MessageMapper {
    Message toEntity(MessageDTO messageDTO);
    MessageSimpleDTO toSimpleDto(Message entity);
}
