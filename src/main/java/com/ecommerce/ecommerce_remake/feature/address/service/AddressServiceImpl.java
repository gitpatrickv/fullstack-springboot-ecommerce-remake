package com.ecommerce.ecommerce_remake.feature.address.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.common.util.mapper.ModelToEntityMapper;
import com.ecommerce.ecommerce_remake.feature.address.model.Address;
import com.ecommerce.ecommerce_remake.feature.address.model.AddressModel;
import com.ecommerce.ecommerce_remake.feature.address.repository.AddressRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.NotImplementedException;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class AddressServiceImpl extends CrudService implements AddressService {

    private final AddressRepository addressRepository;
    private final Validator validator;
    private final UserService userService;

    private ModelToEntityMapper<AddressModel, Address> modelToEntityMapper = new ModelToEntityMapper<>(Address.class);
    private EntityToModelMapper<Address, AddressModel> entityToModelMapper = new EntityToModelMapper<>(AddressModel.class);
    @Transactional
    @Override
    protected <T extends Model> Model save(T model) {
        User user = userService.getCurrentAuthenticatedUser();
        Address address = modelToEntityMapper.map((AddressModel) model);
        address.setStatus(Status.INACTIVE);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return entityToModelMapper.map(savedAddress);
    }

    @Override
    protected <T extends Model> Optional<Model> getOne(String id) {
        throw new NotImplementedException();
    }

    @Override
    protected GetAllResponse getAll(int pageNo, int pageSize, String sortBy) {
        User user = userService.getCurrentAuthenticatedUser();
        List<Address> addresses = user.getAddresses();
        List<AddressModel> addressModels = addresses.stream()
                .map(address -> entityToModelMapper.map(address))
                .sorted(Comparator.comparing(AddressModel::getStatus))
                .toList();
        return new GetAllResponse(addressModels, new PageResponse(0,0, (long) addresses.size(),0,true));
    }
    @Transactional
    @Override
    protected <T extends Model> Model updateOne(T model) {
        AddressModel addressModel = (AddressModel) model;
        return this.getAddressById(addressModel.getAddressId().toString())
                .map(address -> {
                    Optional.ofNullable(addressModel.getFullName())
                            .ifPresent(address::setFullName);
                    Optional.ofNullable(addressModel.getContactNumber())
                            .ifPresent(address::setContactNumber);
                    Optional.ofNullable(addressModel.getStreetAddress())
                            .ifPresent(address::setStreetAddress);
                    Optional.ofNullable(addressModel.getCity())
                            .ifPresent(address::setCity);
                    Optional.ofNullable(addressModel.getPostCode())
                            .ifPresent(address::setPostCode);
                    Optional.ofNullable(addressModel.getAddressType())
                            .ifPresent(address::setAddressType);

                    Address savedAddress = addressRepository.save(address);
                    return entityToModelMapper.map(savedAddress);
                }).orElse(null);
    }

    @Transactional
    @Override
    protected void changeStatus(String id, Status status) {
        User user = userService.getCurrentAuthenticatedUser();
        List<Address> addresses = user.getAddresses();

        addresses.forEach(address -> address.setStatus(Status.INACTIVE));

        Optional<Address> optionalAddress = this.getAddressById(id);

        if(optionalAddress.isPresent()){
            Address address = optionalAddress.get();
            address.setStatus(status);
            addressRepository.save(address);
        }
    }

    @Override
    protected String moduleName() {
        return Module.address.getModuleName();
    }

    @Override
    protected Class modelClass() {
        return AddressModel.class;
    }

    @Override
    protected Validator validator() {
        return validator;
    }

    @Override
    public Optional<Address> getAddressById(String id) {
        return addressRepository.findById(Integer.parseInt(id));
    }

    @Override
    public Address findAddressByStatusAndUser(Status status, User user) {
        return addressRepository.findByStatusAndUser(status,user)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found!."));
    }

    @Override
    public void deleteAddressById(String id) {
        addressRepository.deleteById(Integer.parseInt(id));
    }
}
