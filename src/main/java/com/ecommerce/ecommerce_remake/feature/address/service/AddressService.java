package com.ecommerce.ecommerce_remake.feature.address.service;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.address.model.Address;
import com.ecommerce.ecommerce_remake.feature.user.model.User;

import java.util.Optional;

public interface AddressService {
    Optional<Address> getAddressById(String id);
    Address findAddressByStatusAndUser(Status status, User user);
    void deleteAddressById(String id);
}
