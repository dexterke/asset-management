package com.mylab.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PROPERTY_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class AssetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "PROPERTY_TITLE", nullable = false)
    private String title;

    @Column(name = "PROPERTY_DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "PROPERTY_PRICE", nullable = false)
    private Double price;

    @Column(name = "PROPERTY_ADDRESS", nullable = false)
    private String address;

    // FetchType.LAZY: don't fetch userdata while fetching asset
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity userEntity;

}
