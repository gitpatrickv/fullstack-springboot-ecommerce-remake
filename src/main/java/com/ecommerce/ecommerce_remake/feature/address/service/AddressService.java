package com.ecommerce.ecommerce_remake.feature.address.service;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.address.model.Address;

import java.util.Optional;

public interface AddressService {
    Optional<Address> getAddressById(String id);
    Address findAddressByStatusAndUser(Status status, Integer userId);
    void deleteAddressById(String id);
}
