package com.ecommerce.ecommerce_remake.common.util.mapper;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.common.dto.Model;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
@AllArgsConstructor
public class ModelToEntityMapper <M extends Model, E extends AuditEntity>{

    private Class<E> destEntityClass;

    public E map(M model){
        ModelMapper mapper = new ModelMapper();
        return (E)mapper.map(model, this.destEntityClass);
    }

}

