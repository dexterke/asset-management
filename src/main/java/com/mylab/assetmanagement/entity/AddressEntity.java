package com.mylab.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ADDRESS_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class AddressEntity {

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

}
