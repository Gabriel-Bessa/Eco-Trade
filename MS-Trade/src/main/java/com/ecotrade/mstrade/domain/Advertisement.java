package com.ecotrade.mstrade.domain;

import com.ecotrade.mstrade.domain.enuns.AdvertisementStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_advertisement")
public class Advertisement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_advertisement")
    @SequenceGenerator(name = "seq_advertisement", sequenceName = "trade.seq_advertisement", allocationSize = 1)
    private Long id;

    @Column(name = "product")
    private String product;

    @Column(name = "title")
    private String title;

    @Column(name = "trade_possibility")
    private String tradePossibility;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AdvertisementStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<Offer> offers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertiser_id")
    private User advertiser;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tb_advertisement_file",
    joinColumns = {@JoinColumn(name = "advertiser_id")},
    inverseJoinColumns = {@JoinColumn(name = "file_id")})
    private List<UploadFile> photos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accepted_offer")
    private Offer acceptedOffer;
}
