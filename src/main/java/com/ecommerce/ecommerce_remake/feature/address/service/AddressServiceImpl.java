package com.ecommerce.ecommerce_remake.feature.address.service;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.address.model.Address;
import com.ecommerce.ecommerce_remake.feature.address.repository.AddressRepository;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Optional<Address> getAddressById(String id) {
        return addressRepository.findById(Integer.parseInt(id));
    }

    @Override
    public Address findAddressByStatusAndUser(Status status, Integer userId) {
        return addressRepository.findByStatusAndUser_UserId(status, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found!."));
    }

    @Override
    public void deleteAddressById(String id) {
        addressRepository.deleteById(Integer.parseInt(id));
    }
}
