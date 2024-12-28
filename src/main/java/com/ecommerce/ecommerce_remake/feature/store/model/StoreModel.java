package com.ecommerce.ecommerce_remake.feature.store.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.marker.CreateInfo;
import com.ecommerce.ecommerce_remake.common.marker.UpdateInfo;
import com.ecommerce.ecommerce_remake.validator.UniqueContactNumberValid;
import com.ecommerce.ecommerce_remake.validator.UniqueStoreValid;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
public class StoreModel extends Model {

    @Null(groups = CreateInfo.class)
    @NotNull(groups = UpdateInfo.class)
    private Integer storeId;
    @UniqueStoreValid
    @NotNull(message = "{store.name.required}")
    private String storeName;
    @UniqueContactNumberValid
    @NotNull(message = "{contact.number.required}")
    private String contactNumber;
    private String picture;
    @Enumerated(EnumType.STRING)
    private Status status;
}
