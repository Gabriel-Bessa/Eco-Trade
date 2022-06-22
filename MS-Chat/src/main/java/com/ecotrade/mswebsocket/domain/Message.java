package com.ecotrade.mswebsocket.domain;

import com.ecotrade.mswebsocket.domain.enuns.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_message")
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_message")
    @SequenceGenerator(name = "seq_message", sequenceName = "seq_message", allocationSize = 1)
    private Long id;

    @Column(name = "send_at")
    private LocalDateTime sendAt;

    @Column(name = "text")
    private String text;

    @Column(name = "user_target")
    private Long userTarget;

    @Column(name = "user_source")
    private Long userSource;

    @Column(name = "advertisement_id")
    private Long advertisementId;

    @Column(name = "offer_id")
    private Long offerId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
}
