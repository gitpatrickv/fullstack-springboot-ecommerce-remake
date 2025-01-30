package com.ecommerce.ecommerce_remake.common.util.mapper;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.common.dto.Model;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class EntityToModelMapper <E extends AuditEntity, M extends Model> {

    private Class<M> destModelClass;

    public M map(E entity){
        ModelMapper mapper = new ModelMapper();
        return (M) mapper.map(entity, this.destModelClass);
    }
}
