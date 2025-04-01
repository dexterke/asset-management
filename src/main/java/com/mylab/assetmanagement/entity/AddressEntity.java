package com.mylab.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ADDRESS")
@Getter
@Setter
@NoArgsConstructor
public class AddressEntity {

    public enum ADDRESS_TYPE {
        ASSET, PRIMARY;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "HOUSE_NO", nullable = true)
    private String houseNo;

    @Column(name = "STREET", nullable = true)
    private String street;

    @Column(name = "CITY", nullable = true)
    private String city;

    @Column(name = "POSTAL_CODE", nullable = true)
    private String postalCode;

    @Column(name = "COUNTRY", nullable = true)
    private String country;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity userEntity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ASSET_ID", nullable = true)
    private AssetEntity assetEntity;

    @Column(name = "TYPE", columnDefinition = "INT NOT NULL DEFAULT '0'")
    private int type;
}
