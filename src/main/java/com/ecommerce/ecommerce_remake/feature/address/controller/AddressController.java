package com.ecommerce.ecommerce_remake.feature.address.controller;

import com.ecommerce.ecommerce_remake.feature.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final AddressService addressService;

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAddressById(@PathVariable("id") String id){
        log.info("Received request to delete address with id {}", id);
        addressService.deleteAddressById(id);
        log.info("Successfully deleted address with id {}", id);
    }

}
