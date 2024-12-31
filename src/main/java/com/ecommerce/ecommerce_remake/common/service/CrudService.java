package com.ecommerce.ecommerce_remake.common.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.ResponseCode;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.Response;
import com.ecommerce.ecommerce_remake.common.marker.CreateInfo;
import com.ecommerce.ecommerce_remake.common.marker.DataValidation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CrudService {

    private static final Logger log = LoggerFactory.getLogger(CrudService.class);
    protected abstract <T extends Model> Model save(T model);
    protected abstract <T extends Model> Model getOne(String id);
    protected abstract GetAllResponse getAll(int pageNo, int pageSize, String sortBy);
    protected abstract String updateOne();
    protected abstract void changeOneState(String id, Status status);
    protected abstract String moduleName();
    protected abstract Class modelClass();
    protected abstract Validator validator();

    public final Response create(String jsonRequest){
        Model object = Model.parse(jsonRequest, this.modelClass());
        this.validateFormat(object, CreateInfo.class);
        Model saved = this.save(object);
        log.info("Saving {}: {}", this.moduleName(), saved);
        return new Response(ResponseCode.RESP_SUCCESS, String.format("Saved %s record to DB", this.moduleName()), saved);
    }

    public final Response retrieve(String id){
        log.info("ID provided, retrieving {} record for id: {}", this.moduleName(), id);
        Model obj = this.getOne(id);
        if (obj == null) {
            log.info("Record for {} id '{}' not found", this.moduleName(), id);
            return new Response(ResponseCode.RESP_NOT_FOUND, String.format("Record for %s id %s not found", this.moduleName(), id), id);
        } else {
            log.info("Object of type '{}' found", this.moduleName());
            return new Response(ResponseCode.RESP_SUCCESS, String.format("Found record for ID = %s", id), obj);
        }
    }

    public final Response retrieveAll(int pageNo, int pageSize, String sortBy){
        log.info("retrieving all {}(s)", this.moduleName());
        GetAllResponse found = this.getAll(pageNo, pageSize, sortBy);
        if (found.getModels().isEmpty()) {
            return new Response(ResponseCode.RESP_NOT_FOUND, "No {} record found", this.moduleName());
        } else {
            log.info("Retrieved {} {} objects", found.getModels().size(), this.moduleName());
            return new Response(ResponseCode.RESP_SUCCESS, String.format("Retrieved %s %s", found.getModels().size(), this.moduleName()) + " objects", found);
        }
    }

    public final String update(){
        log.info("Updated {} ", this.moduleName());
        return this.updateOne();
    }

    public final Response changeState(String id, Status status) {
        this.changeOneState(id, status);
        return new Response(ResponseCode.RESP_SUCCESS, String.format("Successfully change status for %s ID %s", this.moduleName(), id));
    }

    private void validateFormat(Model obj, Class group) {
        Validator validator = this.validator();
        Set<ConstraintViolation<Model>> violations = validator.validate(obj, new Class[0]);
        Set<ConstraintViolation<Model>> groupValViolations = validator.validate(obj, new Class[]{group});
        Set<ConstraintViolation<Model>> allViolations = (Set) Stream.of(violations, groupValViolations)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        if (!allViolations.isEmpty()) {
            throw new ConstraintViolationException(allViolations);
        } else {
            Set<ConstraintViolation<Model>> dataValidation = validator.validate(obj, new Class[]{DataValidation.class});
            if (!dataValidation.isEmpty()) {
                throw new ConstraintViolationException(dataValidation);
            }
        }
    }
}
