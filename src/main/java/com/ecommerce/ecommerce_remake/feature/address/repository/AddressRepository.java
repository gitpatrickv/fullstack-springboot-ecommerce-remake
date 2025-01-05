package com.ecommerce.ecommerce_remake.feature.address.repository;

import com.ecommerce.ecommerce_remake.feature.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
