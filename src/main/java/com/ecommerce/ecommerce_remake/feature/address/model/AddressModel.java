package com.ecommerce.ecommerce_remake.feature.address.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.marker.CreateInfo;
import com.ecommerce.ecommerce_remake.common.marker.UpdateInfo;
import com.ecommerce.ecommerce_remake.feature.address.enums.AddressType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "{name.required}")
    private String fullName;
    @NotBlank(message = "{contact.number.required}")
    private String contactNumber;
    @NotBlank(message = "{address.required}")
    private String streetAddress;
    @NotBlank(message = "{city.required}")
    private String city;
    @NotBlank(message = "{post.code.required}")
    private String postCode;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    @Enumerated(EnumType.STRING)
    private Status status;
}
