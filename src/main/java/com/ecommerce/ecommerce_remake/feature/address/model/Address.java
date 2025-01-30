package com.ecommerce.ecommerce_remake.feature.address.model;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.address.enums.AddressType;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    private String fullName;
    private String contactNumber;
    private String streetAddress;
    private String city;
    private String postCode;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
