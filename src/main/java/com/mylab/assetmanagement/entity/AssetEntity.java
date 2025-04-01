package com.mylab.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ASSET")
@Getter
@Setter
@NoArgsConstructor
public class AssetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    private AddressEntity addressEntity;

    // FetchType.LAZY: don't fetch userdata while fetching asset
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity userEntity;

}
