package com.ecommerce.ecommerce_remake.feature.address.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.marker.CreateInfo;
import com.ecommerce.ecommerce_remake.common.marker.UpdateInfo;
import com.ecommerce.ecommerce_remake.feature.address.enums.AddressType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel extends Model {

    @Null(groups = CreateInfo.class)
    @NotNull(groups = UpdateInfo.class)
    private Integer addressId;
    @NotNull(message = "{name.required}")
    private String fullName;
    @NotNull(message = "{contact.number.required}")
    private String contactNumber;
    @NotNull(message = "{address.required}")
    private String streetAddress;
    @NotNull(message = "{city.required}")
    private String city;
    @NotNull(message = "{post.code.required}")
    private String postCode;
    @NotNull(message = "{address.type.required}", groups = CreateInfo.class)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    @Enumerated(EnumType.STRING)
    private Status status;
}
