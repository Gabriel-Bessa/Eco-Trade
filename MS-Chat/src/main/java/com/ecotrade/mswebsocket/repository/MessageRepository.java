package com.ecotrade.mswebsocket.repository;

import com.ecotrade.mswebsocket.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT msg FROM Message msg WHERE " +
            "msg.advertisementId = :advertisementId " +
            "AND msg.offerId = :offerId " +
            "AND (msg.userTarget = :userAuth OR msg.userSource = :userAuth)")
    List<Message> findMessages(Long advertisementId, Long offerId, Long userAuth);
}
